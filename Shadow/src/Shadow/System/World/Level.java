package Shadow.System.World;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Shadow.System.Graphics.Color;
import Shadow.System.Graphics.Lights.Light;
import Shadow.System.Graphics.Lights.LightPositional;
import Shadow.System.Graphics.Materials.Material;
import Shadow.System.Graphics.Screen.SpriteSheet;
import Shadow.System.Graphics.Screen.Viewport;
import Shadow.System.World.Geometry.GeometryBuffer;
import Shadow.System.World.Tiles.Tile;
import Shadow.Util.Math.Point;
import Shadow.Util.Math.Vector;

public class Level {
	
	private static final int HEADERSIZE = 2;
	
	private int w, h;
	private int[] tiles;
	private int[] layerBackground;
	private int[] layerIntermediate;
	private int[] layerOverlay;
	
	private GeometryBuffer gbuf = new GeometryBuffer();
	private List<Light> lights = new ArrayList<>();
	
	public static final String test = "src/res/testlevel.lvl";
	
	public Level(String path) {
		loadLevel(path);
	}
	
	public void init() {
		lights.add(new LightPositional(0, 0, 50));
		lights.add(new LightPositional( new Color(0.1, 0.1, 0.1, 1D),
									    new Color(0.9, 0.9, 0.9, 1D),
										new Color(0.9, 0.9, 0.9, 1D),
										30, 180, 30));
	}
	
	public void tick() {
	}
	
	
	public void render(Viewport vp) {
		
		//Per tile calculations
		for (int y = 0; y < h * Tile.TILESIZE; y += Tile.TILESIZE) {
			for (int x = 0; x < w * Tile.TILESIZE; x += Tile.TILESIZE) {
				Tile t = getTile(x, y);
				Material currMaterial = t.getMaterial();
//				vertices.add(new Point(x, y + Tile.TILESIZE)); //bottom-left vertex
//				vertices.add(new Point(x + Tile.TILESIZE, y + Tile.TILESIZE)); //bottom-right vertex
//				vertices.add(new Point(x + Tile.TILESIZE, y)); //top-right vertex
//				vertices.add(new Point(x, y)); //top-left vertex
				
//				normals.add(new Vector(0, 0, 1)); //we add a normal to each vertex so that if we wish we can offset them and interpolate for effects
//				normals.add(new Vector(0, 0, 1));
//				normals.add(new Vector(0, 0, 1));
//				normals.add(new Vector(0, 0, 1));
				
				
				//Per pixel calculations
				for(int yy = 0; yy < 8; ++yy) {
					for(int xx = 0; xx < 8; ++xx) {
						int xp = x + xx;
						int yp = y + yy;
						int tile = t.id;
						int tx = t.id % SpriteSheet.testtiles.getWidth() + xx;
						int ty = t.id / SpriteSheet.testtiles.getHeight() + yy;
						
						
						int pix = SpriteSheet.testtiles.getPixel(tx, ty); //vp.getPixel(xx, yy);
						int a = (pix >> 24) & 0xff;
						int r = (pix >> 16) & 0xff;
						int g = (pix >> 8) & 0xff;
						int b = pix & 0xff;
						Color shadePix = new Color(r, g, b, a);
						
						
						double contribTotalR = 0D;
						double contribTotalG = 0D;
						double contribTotalB = 0D;
						double contribTotalA = 1D;
						
						//per light contribution calculations
						for(int i = 0; i < lights.size(); ++i) {
							//calculate contribution by positional lights in the level
							Light tmp = lights.get(i);
							if(tmp instanceof LightPositional) {
								double nx = 0, ny = -0.6, nz = 0.8; //this normal SHOULD be normalized if you choose elements such that ||n|| > 1
								
								/*
								 * Do necessary calculations for the diffuse term
								 */
								double sx = (tmp.x - xp), sy = (tmp.y - yp), sz = (tmp.z - 0); //vector to (s)ource of light
								double slen = invSqrt(sx * sx + sy * sy + sz * sz);
								
								sx /= slen; //normalize the light source to world position vector
								sy /= slen;
								sz /= slen;
								
								double lambert = (sx * nx) + (sy * ny) + (sz * nz); //lambert's emission law (cos(a) = dot(norm(s), norm(n)))
								lambert = Math.max(0, lambert);
								
								/*
								 * Do necessary calculations for the specular term
								 */
								double vx = (vp.getCamX() - xp), vy = (vp.getCamY() - yp), vz = (vp.getCamZ() - 0); //vector to (v)iewer / eye
								double hx = vx + sx, hy = vy + sy, hz = vz + sz; //Blinn's halfway vector h = v + s
								double hlen = Math.sqrt(hx * hx + hy * hy + hz * hz);
								
								hx /= hlen; //normalize the halfway vector
								hy /= hlen;
								hz /= hlen;
								
								double blinn = hx * nx + hy * ny + hz * nz; //Blinn-Phong specular reflection (cos(b) = dot(norm(h), norm(n)))
								blinn = Math.max(0, Math.pow(blinn, currMaterial.specularPow));
								
								/*
								 * Diffuse contributions
								 */
								double contribDiffuseR = shadePix.r * tmp.diffuseColor.r * currMaterial.diffuseCoeffR * lambert;
								double contribDiffuseG = shadePix.g * tmp.diffuseColor.g * currMaterial.diffuseCoeffG * lambert;
								double contribDiffuseB = shadePix.b * tmp.diffuseColor.b * currMaterial.diffuseCoeffB * lambert;
								
								/*
								 * Specular contributions
								 */
								double contribSpecularR = shadePix.r * tmp.specularColor.r * currMaterial.specularCoeffR * blinn;
								double contribSpecularG = shadePix.g * tmp.specularColor.g * currMaterial.specularCoeffG * blinn;
								double contribSpecularB = shadePix.b * tmp.specularColor.b * currMaterial.specularCoeffB * blinn;
								
								/*
								 * Add all direct and indirect light contributions
								 */
								contribTotalR = contribTotalR + currMaterial.ambientCoeffR * tmp.ambientColor.r + contribDiffuseR + contribSpecularR;
								contribTotalG = contribTotalG + currMaterial.ambientCoeffG * tmp.ambientColor.g + contribDiffuseG + contribSpecularG;
								contribTotalB = contribTotalB + currMaterial.ambientCoeffB * tmp.ambientColor.b + contribDiffuseB + contribSpecularB;
							}
						}
						
						shadePix.set(contribTotalR, contribTotalG, contribTotalB, contribTotalA);
						
						vp.setPixel(shadePix, xp, yp);
					}
				}
			}
		}
	}
	
	public void bufferTiles() {
		for(int y = 0; y < h; ++y) {
			for(int x = 0; x < w; ++x) {
				//buffer by layer depth
				if(layerOverlay[x + y * w] > 0) {
					tiles[x + y * w] = layerOverlay[x + y * w];
				}
				else if(layerIntermediate[x + y * w] > 0) {
					tiles[x + y * w] = layerIntermediate[x + y * w];
				}
				else {
					tiles[x + y * w] = layerBackground[x + y * w];
				}
			}
		}
	}
	
	/*
	 * Format:
	 * Header contains {w h}
	 * {LayerBG, LayerIM, LayerOL}
	 */
	public void loadLevel(String path) {
		System.out.println("Loaded level");
		try {
			Scanner scanner = new Scanner(new File(path));
			w = scanner.nextInt();
			h = scanner.nextInt();
			System.out.println("The current level \"" + path + "\" has dimension (" + w + ", " + h + ")");
			tiles = new int[w * h];
			layerBackground = new int[w * h];
			layerIntermediate = new int[w * h];
			layerOverlay = new int[w * h];
			int count = 0;
			while(scanner.hasNextInt()) {
				layerBackground[count] = scanner.nextInt();
				layerIntermediate[count] = scanner.nextInt();
				layerOverlay[count++] = scanner.nextInt();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		bufferTiles();
	}
	
	public final double getAmbientTerm() {
		return 0.05D;
	}
	
	public static double invSqrt(double number){
	    double x = number;
	    double xhalf = 0.5d * x;
	    long i = Double.doubleToLongBits(x);
	    i = 0x5fe6ec85e7de30daL - (i >> 1);
	    x = Double.longBitsToDouble(i);
//	    for(int it = 0; it < 1; ++it){
	        x = x * (1.5d - xhalf * x * x);
//	    }
	    x *= number;
	    return x;
	}
	
	public int getTileType(int x, int y) {
		x >>= 3;
		y >>= 3;
		if(x < 0 || x >= w || y < 0 || y >= h) return -1; //error id, will cause array out of bounds
		return tiles[x + y * w];
	}
	
	public Tile getTile(int x, int y) {
		x >>= 3;
		y >>= 3;
//		System.out.println("Getting tile at (" + x + ", " + y + ")" + ", tile type " + getTileType(x, y) + "!");
		if(x < 0 || x >= w || y < 0 || y >= h) return null;
		return Tile.tiles[getTileType(x, y)];
	}
	
	public void setTileType(int id, int x, int y) {
		x >>= 3;
		y >>= 3;
		if(x < 0 || x >= w || y < 0 || y >= h) return;
		tiles[x + y * w] = id;
	}
	
	public void setTile(Tile t, int x, int y) {
		x >>= 3;
		y >>= 3;
		if(x < 0 || x >= w || y < 0 || y >= h) return;
		setTileType(t.id, x, y);
	}
}
