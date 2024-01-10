
var map;
 var coord1Input = document.getElementById("coord1");
 var coord2Input = document.getElementById("coord2");
 var marker1;
 var marker2;
 let counter = 0;

 var leftBottom =  ol.proj.transform([7.0390799173829555,50.6616799499222], "EPSG:4326", "EPSG:3857");
 var rightTop    = ol.proj.transform([7.172951,50.78453], "EPSG:4326", "EPSG:3857");
 var minx = leftBottom[0];
 var miny = leftBottom[1];
 var maxx = rightTop[0];
 var maxy = rightTop[1];

tile_layer = new ol.layer.Tile({ source: new ol.source.OSM() });
var oldZoom = 3;

const turf = require('@turf/turf');

var map = new ol.Map({
	target: 'map',
	layers: [
		tile_layer
	],
	view: new ol.View({
		center: ol.proj.fromLonLat([7.086, 50.727]),
		zoom: oldZoom,
		maxZoom: 20,
		minZoom: 9,
	})
});
marker1 = new ol.Feature({
  geometry: new ol.geom.Point(1,1)
});

const marker1Icon =
  new ol.style.Style({
    image: new ol.style.Icon({
      crossOrigin: 'anonymous',
      src: 'assets/source.png',
      scale: "0.08"
    }),
});
marker1.setStyle(marker1Icon);

marker2 = new ol.Feature({
  geometry: new ol.geom.Point(1,1)
});
const marker2Icon =
  new ol.style.Style({
    image: new ol.style.Icon({
      crossOrigin: 'anonymous',
      src: 'assets/target.png',
      scale: "0.08"
    }),
  });
marker2.setStyle(marker2Icon);
map.on("click", function (e) {
      var position = ol.proj.toLonLat(e.coordinate);
      if (counter%2 === 0) {
        marker1.getGeometry().setCoordinates(e.coordinate);
        var markerLayer = new ol.layer.Vector({
          source: new ol.source.Vector({
            features: [marker1]
          })
        });
        map.addLayer(markerLayer);

          coord1Input.value = position[0].toFixed(7) + "," + position[1].toFixed(7);
        counter++;
      } else if (counter%2 === 1) {
        marker2.getGeometry().setCoordinates(e.coordinate);
        var markerLayer = new ol.layer.Vector({
          source: new ol.source.Vector({
            features: [marker2]
          })
        });
        map.addLayer(markerLayer);
        coord2Input.value = position[0].toFixed(7) + "," + position[1].toFixed(7);

        counter++;
        fieldsGetter()
      }
    });
 function fieldsGetter() {
     console.log("This Function getter of Fields")

     var coord1 = coord1Input.value.split(",");
     var coord2 = coord2Input.value.split(",");
     var lat1 = parseFloat(coord1[1]);
     var lon1 = parseFloat(coord1[0]);
     var lat2 = parseFloat(coord2[1]);
     var lon2 = parseFloat(coord2[0]);
     console.log(lat1,lon1,lat2,lon2);
     ol.proj.setProj4(proj4.dis);
     proj4.defs("EPSG:3044","+proj=utm +zone=32 +ellps=GRS80 +towgs84=565.04,49.91,465.84,1.9848,-1.7439,9.0587,4.0772 +units=m +no_defs +type=crs");
     var webCRS =  ol.proj.get("EPSG:4326");
     var dataCRS = ol.proj.get("EPSG:3044");
     console.log(dataCRS)
     let coord1UTM = ol.proj.transform([lat1, lon1], "EPSG:4326", "EPSG:3044");
     let coord2UTM = ol.proj.transform([lat2,   lon2], "EPSG:4326", "EPSG:3044");
     console.log(coord1UTM,coord2UTM);
     let xmin = coord1UTM[0];
     let ymin = coord1UTM[1];
     let xmax = coord2UTM[0];
     let ymax = coord2UTM[1];

     console.log(coord1UTM[0],
                coord1UTM[1],
                coord2UTM[0],
                coord2UTM[1],

         );

     const url = `http://localhost:8080/api/fields/all`
     fetch(url)
         .then(response => {
             if (response.ok) {
                 return response.json();
             } else {
                 throw new Error('Connection is unsuccessful.');
             }
         })
         .then(data => {

             console.log('Server respond:', data);

         })
         .catch(error => {
             console.error('Connection is unsuccessful.', error);
         });
 }



