# Raytrace4s
A Scala based ray tracer. 95% pure functional.

![A sample rendering](https://raw.githubusercontent.com/ToddBodnar/raytrace4s/master/renders/test22.png)
![A sample rendering](https://raw.githubusercontent.com/ToddBodnar/raytrace4s/master/renders/mage.png)
![A sample rendering](https://raw.githubusercontent.com/ToddBodnar/raytrace4s/master/renders/polyCollectionMaterials.png)

## About
A project based on [Peter Shirley's raytracing series](https://www.amazon.com/Ray-Tracing-Weekend-Minibooks-Book-ebook/dp/B01B5AODD8).

For sample renders from various steps along development, see the `./renders/` directory.


## Usage

You can launch this through a simple command line call like `sbt "run polyCollectionMaterials fastbig"`.

More specifically, the `run` command takes two parameters, the scene name (`polyCollectionMaterials` in the example, see below)
and the render settings (`fastbig` in the example above).

The render settings are based in the `/render_settings` folder, and three default settings are provided:

1. `fast` - Renders a small 100x50 render, with very little extra details applied
2. `fastbig` - The same as `fast` but with 10x the resolution
3. `print` - Much slower, but includes enough rays and bounces that this should look pretty realilistc even for complex scenes.

For more details, see `Render Settings` below

### Configuring Scene Files

Scene files define the layout of everything in the render job and are normally stored in the `/scenes` directory.

A scene generally describes three things: the camera, the objects, and the background

#### Configuring the camera

The camera is configured under the path `camera`, and a whole config would look something like:

```json
{
	"camera": {
		"fov": 5,
		"aspect": 2,
		"origin": {
				"x": -320,
				"y": 30.0,
				"z": -320
		},
		"up": {
			"x": 0,
			"y": 1,
			"z": 0
		},
		"target": {
				"x": 0,
				"y": 15.0,
				"z": 0
		},
		"aperature": 0.01,
		"focus_distance": 452.789
	}
  ...
}  
```

Fields:

- `fov` - the field of view
- `aspect` - the expected aspect ratio (width / height)
- `origin` - the coordinates where the camera is placed
- `up` - A unit vector describing which way is "up"
- `target` - The camera is pointed at this location
- `aperature` - The camera's aperature, larger numbers will increase blur for out of focus elements
- `focus_distance` - If an object is this distance away from `origin`, it will be perfectly in focus 

#### Configuring objects

Objects are described as a list under the `objects` path. Raytrace4s supports several types of objects:

- Spheres
- Triangles (polygons)
- Platonic Solids
- Imported objects

Additionally, objects may use a common `material` definition (see below)

In general, an object will have the following common fields, which we'll ignore in the 
individual object's documentation for space:

- `type` - The type of the object
- `center` - The coordinates for the center of the object
- `rotation` (Optional) - The rotation, defined by `yaw`, `pitch` and `roll` (radians)
- `material` - The material of the sphere, see below

##### Configuring Spheres

Internal Name: `sphere`

Sample Config:

```json
{
  "type": "sphere",
  "center": {
    "x": 4,
    "y": 0.5,
    "z": 0
  },
  "rotation":{
    "yaw":1,
    "pitch":0,
    "roll":0
  },
  "scale": 1,
  "material": {
    ...
  }
}
```

Parameters:
- `scale` - The radius


##### Configuring Polygons

Internal Name: `triangle`

Sample Config:

```json
{
  "type": "triangle",
  "center": {
    "x": 0,
    "y": 0.0,
    "z": 0
  },
  "rotation":{
    "yaw":0,
    "pitch":0,
    "roll":0
  },
  "v3": {
    "x": 0.7,
    "y": 0.0,
    "z": 0.0
  },
  "v2": {
    "x": 0.0,
    "y": 1,
    "z": 0.0
  },
  "v1": {
    "x": -0.7,
    "y": 0.0,
    "z": 0.0
  },
  "material": {
    ...
  }
}
```

Parameters:
- `v1` - A point on the polygon
- `v2` - A point on the polygon
- `v3` - A point on the polygon

Notes:
We do not care about culling polygons based on the ordering of the three points


##### Configuring Platonic Solids

Internal Name: `platonic`

Sample Config:

```json
{
  "type": "platonic",
  "subType": "pyramid",
  "center": {
    "x": 6,
    "y": 0.0,
    "z": 0
  },
  "rotation":{
    "yaw":0.5,
    "pitch":0.5,
    "roll":0.5
  },
  "material": {
    ...
  }
}
```

Parameters:
- `subType` - The specific type of platonic solid


##### Configuring an Imported Model

Internal Name: `imported`

Sample Config:

```json
{
  "type": "imported",
  "subType": "stl_bin",
  "fileName": "shapefiles/teapot.stl",
  "center": {
    "x": 0,
    "y": 0.0,
    "z": 0
  },
  "rotation": {
    "yaw": 0.0,
    "pitch": 0.00,
    "roll": 0.0
  },
  "material": {
    ...
  }
}
```

Parameters:
- `subType` - The type of the file being imported, currently we just support 
    `stl_bin` files (binary STL format).
- `fileName` - The name of the file to read

#### Configuring Materials

A `Material` describes what an object looks like, for example: Is it red? Is it 
reflective? Is it transparent? Does it emit light? 

We currently support the following materials:

- Color Material
- Light Emitting Material
- Texture Material
- Custom Material

##### Color Materials

A simple material based on `r`, `g`, and `b` parameters (range 0 to 1)

##### Light Emitting Material

Generally used for point lights, if needed. Configured the same as a color 
material, but without a limit on the upper bound of the color parameters.

##### Custom Material

A fully customizable material

Parameters:

- `texture` - The texture, see below
- `diffuseAmount` - The "fuzziness" of any light that bounces off of this object
- `lightDampening` - How much light is absorbed by this object, instead of bounced back out
- `reflectionAmount` - How reflective the surface is (0 -> no reflection, 1 -> complete reflection)
- `reflectionFuzziness` - How "fuzzy" the reflection should be
- `transparency` - How transparent the object is (0 -> completely opaque, 1 -> completely transparent)
- `refractionIndex` - The index of refraction, which describes how much the light is bent when going
     through some transparent object. If 1 or below, it'll pass straight through. See [wikipedia](https://en.wikipedia.org/wiki/Refractive_index#Typical_values) for more examples.

##### Texture Materials

A `texture` is something painted on top of an object, like an image. We support:

- Color textures
- Checkered Box textures
- Perlin Noise based textures
- Image textures

###### Color textures

Like the `Color Materials`, a simple config based on `r`, `g`, and `b` parameters (range 0 to 1).

###### Checkered Box textures

Divides the world into a checkered box of cubes with a size of `scale`. This 
defines alternating between `textureOne` and `textureTwo` (recursivly, two 
other textures.)

###### Perlin Noise textures

Like a `Checkered Box` texture, but the merging of the two textures is based 
on the traditional Perlin noise algorithm.

###### Image Texture

An image is loaded from the `image` field and applied to the object.

#### Configuring The Sky

The `sky` is a special object that is infinitly away and emits it's own light.

We support three sky objects:

- `dark` (default) - A black background with no details
- `light` - A simple blue sky
- `texture` - A sky based on a defined texture (see above)

## License 

### Software / Renders

Copyright 2016-2018, 2022 Todd Bodnar

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

### Input Images

See images/licenses.md for more info