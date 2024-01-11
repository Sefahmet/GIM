const tile_layer = new ol.layer.Tile({ source: new ol.source.OSM() });
const oldZoom = 3;
const URL = "http://localhost:8080/api/fieldswfs/all";
let geoData;


const map = new ol.Map({
	target: 'map',
	layers: [
		tile_layer
	],
	view: new ol.View({
		center: ol.proj.fromLonLat([6.990853, 50.620320]),
		zoom: oldZoom,
		maxZoom: 20,
		minZoom: 15,
	})
});



fetch(URL).then(response => {
      if (response.ok) {
          return response.json();
      } else {
          throw new Error('Connection is unsuccessful.');
      }
  }).then(data => {

      geoData = data.map(item => item.geometry);

      const olGeometries = geoData.map((geometry) => {
        return geometry.map((point) => {
            return ol.proj.fromLonLat([point.y, point.x]);
        });
      });

      const polygonStyle = new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: 'blue', // line
          width: 3 //linewidth
        }),
        fill: new ol.style.Fill({
          color: 'rgba(146,104,41, 1)' //fill
        })
      });

      const vectorLayer = new ol.layer.Vector({
        source: new ol.source.Vector({
            features: olGeometries.map(function (geometry) {
                return new ol.Feature({
                    geometry: new ol.geom.Polygon([geometry])
                });
            })
        }),
        style: [polygonStyle]
      });
  
      map.addLayer(vectorLayer);

  
  }).catch(error => {
      console.error('Connection is unsuccessful.', error);
  });

const select = new ol.interaction.Select();
map.addInteraction(select);

// Select listener
select.on('select', function(e) {
  e.selected.forEach(function(feature) {
    // Create new still
    const newStyle = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: 'black', // new line color
            width: 3
        }),
        fill: new ol.style.Fill({
            color: 'rgba(255, 255, 0, 1)' // new fill color
        })
    });

    // Apply selection
    feature.setStyle(newStyle);
});
})



map.on("click", function (e) {
  const position = ol.proj.toLonLat(e.coordinate);
  const [lon, lat] = position;
  fetch(`http://localhost:8080/api/fields/point?lat=${lat}&lon=${lon}`)
  .then(res => {
    if(res.status === 200) return res.json()
    })
  .then(res => {
      fid = res.fid;
    document.getElementById("soil").innerHTML = res.soil;
    document.getElementById("fid").innerHTML = res.fid;
  });
  var fid = document.getElementById("fid").innerHTML;

  fetch(`http://localhost:8080/api/species/fid?fid=${fid}`)

  .then(res => {
      console.log(res)
      if(res.status === 200) return res.json()

  })
  .then(res => {
      document.getElementById("plant").innerHTML = res[0].plant;
      document.getElementById("year").innerHTML = res[0].year;
      document.getElementById("subspecies").innerHTML = res[0].subspecies;
  });

    fetch(`http://localhost:8080/api/treatment/fid?fid=${fid}`)

        .then(res => {
            console.log(res)
            if(res.status === 200) return res.json()

        })
        .then(res => {
            document.getElementById("plant").innerHTML = res[0].plant;
            document.getElementById("year").innerHTML = res[0].year;
            document.getElementById("subspecies").innerHTML = res[0].subspecies;
        });





});

