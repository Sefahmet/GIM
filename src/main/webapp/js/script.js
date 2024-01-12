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


const fetchInitial = async () => {

  const res = await fetch(URL);
  const resData = await res.json();
  
  geoData = resData.map(item => item.geometry);

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
};

const fetchPoints = async (lat, lon) => {
  const res = await fetch(`http://localhost:8080/api/fields/point?lat=${lat}&lon=${lon}`)
  const resData = await res.json();
  document.getElementById("soil").innerHTML = resData.soil;
  document.getElementById("fid").innerHTML = resData.fid;
  await fetchFidSpecies(resData.fid);
};

const fetchFidSpecies = async (fid) => {
  const res = await fetch(`http://localhost:8080/api/species/fid?fid=${fid}`);
  const resData = await res.json();
  document.getElementById("plant").innerHTML = resData[0].plant;
  document.getElementById("year").innerHTML = resData[0].year;
  document.getElementById("subspecies").innerHTML = resData[0].subspecies;
};
const fetchFidTreatment = async (fid) => {
  const res = await fetch(`http://localhost:8080/api/treatment/fid?fid=${fid}`);
  const resData = await res.json();
  document.getElementById("plant").innerHTML = resData[0].plant;
  document.getElementById("year").innerHTML = resData[0].year;
  document.getElementById("subspecies").innerHTML = resData[0].subspecies;
};


const select = new ol.interaction.Select();
map.addInteraction(select);

fetchInitial();

map.on("click", function (e) {
  const position = ol.proj.toLonLat(e.coordinate);
  const [lon, lat] = position;
  fetchPoints(lat, lon);
});

select.on('select', function(e) {

  
  e.selected.forEach(function(feature) {
    console.log("feature", feature);
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