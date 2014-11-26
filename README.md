Shadow
======

This is a repo for any engine, system or game related code. It could be anything at any time.

So far it's a completely from-scratch-written (using nothing else but raster data provided by the Java libraries) Blinn-Phong shader with volumetric shadow mapping in 2D.
It features support for two types of shading right now, per-tile and per-pixel. It's implemented solely to run in
software, and for that reason per-pixel shading in realtime with dynamic lighting is not usable, refer to use
per-tile shading as it is feasible as an option.

When there's time I will look into optimization options and I really want to push for the ability to use per-pixel shading
in a realtime application. 

It's a bit unclear what kind of optimizations can be made for the shadow mapping technique I came up with,
past spatial partitioning.

Known bugs:
---------------------
- The shadow mapping at certain light positions reveals tears where it will shade (so far) axial strips even though
it shouldn't. It may be that the AABB intersection with rays implementation has unforeseen corner-cases,
it's also a possibility it is a precision error at some other place.

Unknown bugs:
--------------------
Probably lots!


**Some screenshots for demonstration of what Shadow can do:**
-----------------------------------------------------------------
- http://imgur.com/a/zuIwZ
- http://imgur.com/Yf73Pcb
- http://pasteboard.co/1ZZM0VUR.png
