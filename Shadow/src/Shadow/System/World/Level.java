package Shadow.System.World;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Shadow.ShadowClient;
import Shadow.System.ShadowEngine;
import Shadow.System.Graphics.Color;
import Shadow.System.Graphics.Lights.Light;
import Shadow.System.Graphics.Lights.LightPositional;
import Shadow.System.Graphics.Materials.Material;
import Shadow.System.Graphics.Screen.SpriteSheet;
import Shadow.System.Graphics.Screen.Viewport;
import Shadow.System.World.Geometry.GeometryBuffer;
import Shadow.System.World.Tiles.Tile;
import Shadow.Util.Math.AABB;
import Shadow.Util.Math.Point;
import Shadow.Util.Math.Ray;
import Shadow.Util.Math.Vector;

public class Level {
	
	
	public static final int MAX_DYNAMIC_LIGHTS = 0x20; //sizeof(int) << 3
	
	public static final int STENCIL_LIGHT_MASK = 0x01;
	
	public static final byte MODE_SHADING_PER_PIXEL = 0x01;
	public static final byte MODE_SHADING_PER_TILE = 0x02;
	
	private static final int HEADERSIZE = 2;
	
	private int w, h;
	private int[] tiles;
	private int[] layerBackground;
	private int[] layerIntermediate;
	private int[] layerOverlay;
	private int[] stencilBuffer;
	private byte shadingMode;
	
	private GeometryBuffer gbuf = new GeometryBuffer();
	private List<Light> lights = new ArrayList<>();
	
	public static final String test = "src/res/testlevel.lvl";
	private static final String loadedTiles = "src/res/tilesoutput.txt";
	private static final String bufferDebug = "src/res/bufferdebug.txt";
	
	public Level(String path) {
		loadLevel(path);
	}
	
	
	public void init() {
//		addLight(new LightPositional(30, 20, 25));
//		addLight(new LightPositional(300, 220, 5));
//		addLight(new LightPositional(300, 220, 5));
//		addLight(new LightPositional(300, 220, 5));
		
		addLight(new LightPositional( 	new Color(0.1, 0.1, 0.1, 1D),
			    						new Color(1.0, 1.0, 0.0, 1D),
			    						new Color(1.0, 1.0, 0.0, 1D),
			    						170, 120, 40));
//		
//		addLight(new LightPositional( 	new Color(0.1, 0.1, 0.1, 1D),
//									  	new Color(0.9, 0.9, 0.9, 1D),
//										new Color(0.9, 0.9, 0.9, 1D),
//										240, 200, 50));
		
//		addLight(new LightPositional( 	new Color(0.1, 0.1, 0.1, 1D),
//			    						new Color(0.9, 0.9, 0.9, 1D),
//			    						new Color(0.9, 0.9, 0.9, 1D),
//			    						240, 20, 16));
		
		stencilBuffer = new int[ShadowClient.DEFAULT_WIDTH * ShadowClient.DEFAULT_HEIGHT]; //hack
		writeTiles(loadedTiles);
	}
	
	public void tick() {
	}
	
	
	public void renderPixelShader(Viewport vp) {
		
		shadowVolumePrePass(vp);
		
		//Per tile calculations
		for (int y = 0; y < ShadowClient.DEFAULT_HEIGHT; y += Tile.TILESIZE) {
			for (int x = 0; x < ShadowClient.DEFAULT_WIDTH; x += Tile.TILESIZE) {
				Tile t = getTile(x, y);
				Material currMaterial = t.mat;
				
				//Per pixel calculations
				for(int yy = 0; yy < 8; ++yy) {
					for(int xx = 0; xx < 8; xx++) {
						int xp = x + xx;
						int yp = y + yy;
						
						
						
						int tx = ((t.id % SpriteSheet.testtiles.getWidth()) << 3) + xx; //<< 3 is equivalent of * Tile.TILESIZE
						int ty = ((t.id / SpriteSheet.testtiles.getHeight()) << 3) + yy;
						
						
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
							if(((stencilBuffer[xp + yp * ShadowClient.DEFAULT_WIDTH] >> i) & STENCIL_LIGHT_MASK) == 0) {
								continue;
							}
							//calculate contribution by positional lights in the level
							Light tmp = lights.get(i);
							if(tmp instanceof LightPositional) {
								double nx = 0, ny = 0, nz = 1.0; //this normal SHOULD be normalized if you choose elements such that ||n|| > 1
								
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

	Color shadePix = new Color();
	
	public void renderTileShader(Viewport vp) {
		
		shadowVolumePrePass(vp);
		
		//Per tile calculations
		for (int y = 0; y < vp.getHeight(); y += Tile.TILESIZE) {
			for (int x = 0; x < vp.getWidth(); x += Tile.TILESIZE) {
				Tile t = getTile(x, y);
				Material currMaterial = t.mat;
				
				//Per tile calculations
				boolean hasCalculated = false;
				double contribTotalR = 0D;
				double contribTotalG = 0D;
				double contribTotalB = 0D;
				double contribTotalA = 1D;
				
				double lambert = 0D;
				double blinn = 0D;
				
				double contribDiffuseR = 0D;
				double contribDiffuseG = 0D;
				double contribDiffuseB = 0D;
				
				double contribSpecularR = 0D;
				double contribSpecularG = 0D;
				double contribSpecularB = 0D;
				
				for(int yy = 0; yy < 8; ++yy) {
					for(int xx = 0; xx < 8; ++xx) {
						int xp = x + xx;
						int yp = y + yy;
						
						int tx = ((t.id % SpriteSheet.testtiles.getWidth()) << 3) + xx; //<< 3 is equivalent of * Tile.TILESIZE
						int ty = ((t.id / SpriteSheet.testtiles.getHeight()) << 3) + yy;
						
						int pix = SpriteSheet.testtiles.getPixel(tx, ty); //vp.getPixel(xx, yy);
						int a = (pix >> 24) & 0xff;
						int r = (pix >> 16) & 0xff;
						int g = (pix >> 8) & 0xff;
						int b = pix & 0xff;
						
						shadePix.set(r, g, b, a);
						
						if(!hasCalculated) {
						
						//per light contribution calculations
						for(int i = 0; i < lights.size(); ++i) {
							if(((stencilBuffer[(xp >> 3) + (yp >> 3) * w] >> i) & STENCIL_LIGHT_MASK) == 0) {
								continue;
							}
							//calculate contribution by positional lights in the level
							Light tmp = lights.get(i);
							if(tmp instanceof LightPositional) {
								double nx = 0, ny = 0, nz = 1; //this normal SHOULD be normalized if you choose elements such that ||n|| > 1
								
								/*
								 * Do necessary calculations for the diffuse term
								 */
								double sx = (tmp.x - xp), sy = (tmp.y - yp), sz = (tmp.z - 0); //vector to (s)ource of light
								double slen = invSqrt(sx * sx + sy * sy + sz * sz);
								
								sx /= slen; //normalize the light source to world position vector
								sy /= slen;
								sz /= slen;
								
								lambert = (sx * nx) + (sy * ny) + (sz * nz); //lambert's emission law (cos(a) = dot(norm(s), norm(n)))
								lambert = Math.max(0, lambert);
								
								/*
								 * Do necessary calculations for the specular term
								 */
								double vx = (vp.getCamX() - xp), vy = (vp.getCamY() - yp), vz = (vp.getCamZ() - 0); //vector to (v)iewer / eye
								double hx = vx + sx, hy = vy + sy, hz = vz + sz; //Blinn's halfway vector h = v + s
								double hlen = invSqrt(hx * hx + hy * hy + hz * hz);
								
								hx /= hlen; //normalize the halfway vector
								hy /= hlen;
								hz /= hlen;
								
								blinn = hx * nx + hy * ny + hz * nz; //Blinn-Phong specular reflection (cos(b) = dot(norm(h), norm(n)))
								blinn = blinn * blinn; //Math.max(0, Math.pow(blinn, currMaterial.specularPow));
								
								/*
								 * Diffuse contributions
								 */
								contribDiffuseR = tmp.diffuseColor.r * currMaterial.diffuseCoeffR * lambert;
								contribDiffuseG = tmp.diffuseColor.g * currMaterial.diffuseCoeffG * lambert;
								contribDiffuseB = tmp.diffuseColor.b * currMaterial.diffuseCoeffB * lambert;
								
								/*
								 * Specular contributions
								 */
								contribSpecularR = tmp.specularColor.r * currMaterial.specularCoeffR * blinn;
								contribSpecularG = tmp.specularColor.g * currMaterial.specularCoeffG * blinn;
								contribSpecularB = tmp.specularColor.b * currMaterial.specularCoeffB * blinn;
								
								/*
								 * Add all direct and indirect light contributions
								 */
								contribTotalR = contribTotalR + currMaterial.ambientCoeffR * tmp.ambientColor.r + contribDiffuseR + contribSpecularR;
								contribTotalG = contribTotalG + currMaterial.ambientCoeffG * tmp.ambientColor.g + contribDiffuseG + contribSpecularG;
								contribTotalB = contribTotalB + currMaterial.ambientCoeffB * tmp.ambientColor.b + contribDiffuseB + contribSpecularB;
								
								hasCalculated = true;
								}
							}
						}
						shadePix.set(contribTotalR * shadePix.r, contribTotalG * shadePix.g, contribTotalB * shadePix.b, contribTotalA);
						
						vp.setPixel(shadePix, xp, yp);
					}
				}
			}
		}
	}
	
	/*
	 * Calculate intersections between lights and the shade objects (pixel or tiles)
	 * to determine lit or not by each light
	 * TODO - Consider spatial partitioning for optimization at some point???
	 */
	public void shadowVolumePrePass(Viewport vp) {
		boolean intersected;
		
		for(int y = 0; y < ShadowClient.DEFAULT_HEIGHT; ++y) {
			for(int x = 0; x < ShadowClient.DEFAULT_WIDTH; ++x) {
				stencilBuffer[x + y * ShadowClient.DEFAULT_WIDTH] = 0; //clear
				for(int i = 0; i < lights.size(); ++i) {
					intersected = false;
					Light tmp = lights.get(i);
					Ray r = new Ray(x, getTile(x, y).layer * 15, y, tmp.x, tmp.z, tmp.y);
					for(int j = 0; j < gbuf.bounds.size(); ++j) {
						if(AABB.intersection(r, gbuf.bounds.get(j))) {
							intersected = true;
							break;
						}
					}
					if(!intersected) {
						stencilBuffer[x + y * ShadowClient.DEFAULT_WIDTH] |= (1 << i);
					}
				}
			}
		}
		writeIntBuffer(stencilBuffer, ShadowClient.DEFAULT_WIDTH, ShadowClient.DEFAULT_HEIGHT, bufferDebug);
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
		
		/*
		 * Write bounds to the G-buffer
		 */
		
		int tilesize = Tile.TILESIZE;
		for(int y = 0; y < h * Tile.TILESIZE; y += Tile.TILESIZE) {
			for(int x = 0; x < w * Tile.TILESIZE; x += Tile.TILESIZE) {
				Tile t = getTile(x, y);
				if(t.layer > Tile.LAYER_BACKGROUND) {
					gbuf.addBounds(new AABB(x, 0, y, x + Tile.TILESIZE, 15, y + Tile.TILESIZE));
				}
			}
		}
		
		bufferTiles();
	}
	
	public void writeTiles(String path) {
		try {
			PrintWriter writer = new PrintWriter(path);
			
			for(int y = 0; y < h; ++y) {
				for(int x = 0; x < w; ++x) {
					writer.print(tiles[x + y * w] + " ");
				}
				writer.print("\n");
			}
			
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeIntBuffer(int[] srcbuf, int w, int h) {
		srcbuf = new int[w * h];
		for(int i = 0; i < srcbuf.length; ++i) {
			srcbuf[i] = 0;
		}
	}
	
	public void clearBuffer(int[] srcbuf) {
		for(int i = 0; i < srcbuf.length; ++i) {
			srcbuf[i] = 0;
		}
	}
	
	public void writeIntBuffer(int[] buffer, int w, int h, String path) {
		try {
			PrintWriter writer = new PrintWriter(path);
			
			for(int y = 0; y < h; ++y) {
				for(int x = 0; x < w; ++x) {
					writer.print(buffer[x + y * w] + " ");
				}
				writer.print("\n");
			}
			
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
	
	/*
	 * Intended for readability in getTile, maybe not necessary?
	 */
	private int getTileType(int x, int y) {
		if(x < 0 || x >= w || y < 0 || y >= h) return -1; //error id, will cause array out of bounds
		return tiles[x + y * w];
	}
	
	public Tile getTile(int x, int y) {
		x >>= 3;
		y >>= 3;
		if(x < 0 || x >= w || y < 0 || y >= h) return Tile.voidTile;
		return Tile.tiles[getTileType(x, y)];
	}
	
	/*
	 * Intended for readability in setTile, maybe not necessary?
	 */
	private void setTileType(int id, int x, int y) {
		if(x < 0 || x >= w || y < 0 || y >= h) return;
		tiles[x + y * w] = id;
	}
	
	public void setTile(Tile t, int x, int y) {
		x >>= 3;
		y >>= 3;
		if(x < 0 || x >= w || y < 0 || y >= h) return;
		setTileType(t.id, x, y);
	}
	
	public void addLight(Light l) {
		if(lights.size() > MAX_DYNAMIC_LIGHTS) {
			ShadowEngine.log("Attempted to add more lights than allowed (" + MAX_DYNAMIC_LIGHTS + ")");
			return;
		}
		if(l == null) {
			ShadowEngine.log("Attempted to add null light.");
			return;
		}
		lights.add(l);
	}
	
	public void removeLight(Light l) {
		lights.remove(l);
	}
	
	public void removeLight(int index) {
		lights.remove(index);
	}
	
	public Light getLight(int index) {
		return lights.get(index);
	}
}
