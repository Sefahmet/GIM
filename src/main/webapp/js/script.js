const tile_layer = new ol.layer.Tile({ source: new ol.source.OSM() });
const oldZoom = 3;
const URL = "http://localhost:8080/api/fieldswfs/all";
const ALL_PLANTS_URL = "http://localhost:8080/api/species/all-plants";
const DICTIONARY_URL = "http://localhost:8080/api/fuseki/dictionary";
const DESCRIPTION_URL = "http://localhost:8080/api/fuseki/description?deLabel=";
const IMG_URL = "http://localhost:8080/api/fuseki/image?deLabel=";

let initialData;
let geoData;
let treatmentData;
let uniquePlants;
let plantCheckedList = [];
let checBoxVectorLayer;
let CheckBoxFids = [];
let langChoice = "DE";

let dictionary;

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

  const uniquePlantRes = await fetch(ALL_PLANTS_URL);
  uniquePlants = await uniquePlantRes.json();

  const dictionaryRes = await fetch(DICTIONARY_URL);
  
  dictionary = await dictionaryRes.json();

  initialData = resData;

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



  const checkboxListener = (e) => {
    CheckBoxFids = [];


    if(plantCheckedList.includes(e.target.id)) {
      plantCheckedList = plantCheckedList.filter(item => item !== e.target.id);
    } else {
      plantCheckedList.push(e.target.id);
    }
    plantCheckedList.forEach(plant => {
        // Eğer uniquePlants içinde böyle bir bitki varsa
        if (uniquePlants.hasOwnProperty(plant)) {
            // ChecBoxFids dizisine değerleri ekle
            CheckBoxFids = CheckBoxFids.concat(uniquePlants[plant]);
        }


    }
    );


    map.removeLayer(checBoxVectorLayer);
    const geoData = initialData.filter(item => CheckBoxFids.includes(item.fid)).map(item => item.geometry);
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
        color: 'rgba(146,0,255, 1)' //fill
      })
    });
  
    checBoxVectorLayer  = new ol.layer.Vector({
      source: new ol.source.Vector({
          features: olGeometries.map(function (geometry) {
              return new ol.Feature({
                  geometry: new ol.geom.Polygon([geometry])
              });
          })
      }),
      style: [polygonStyle]
    });
  
    map.addLayer(checBoxVectorLayer);




  }

  // uniquePlants checkbox-table
  const checkBoxTable = document.getElementById("checkbox-table");
  Object.keys(uniquePlants).forEach((uniquePlant) => {
    let inputElement = document.createElement("input");
    let labelElement = document.createElement("label");
    labelElement.innerHTML = uniquePlant;
    inputElement.type = "checkbox";
    inputElement.style.marginRight = "8px";
    inputElement.id = `${uniquePlant}`
    inputElement.addEventListener("change", checkboxListener);
    labelElement.appendChild(inputElement);
    labelElement.style.display = "flex";
    labelElement.style.flexDirection = "row-reverse";
    labelElement.style.justifyContent= "flex-end";
    checkBoxTable.appendChild(labelElement);
  })
  
};

const changeLng = () => {
  const subSpEl = document.getElementById("subspecies");
  const plantEl = document.getElementById("plant");
  const soilEl = document.getElementById("soil");
  
  subSpEl.textContent = dictionary[subSpEl.textContent];
  plantEl.textContent = dictionary[plantEl.textContent];
  soilEl.textContent = dictionary[soilEl.textContent];
}

document.getElementById('toggleBtn').addEventListener('change', function(e) {
    langChoice = e.target.checked ? "EN" : "DE";
    Array.from(document.getElementById("checkbox-table").children).forEach(child => {
      child.firstChild.textContent = dictionary[child.firstChild.textContent];
    })
    changeLng();
  }
);




const fetchPoints = async (lat, lon) => {
  const res = await fetch(`http://localhost:8080/api/fields/point?lat=${lat}&lon=${lon}`)
  const resData = await res.json();

  document.getElementById("soil").innerHTML = langChoice === "EN" ? dictionary[resData.soil] : resData.soil;
  document.getElementById("fid").innerHTML = resData.fid;

  const soilDescRes = await fetch(DESCRIPTION_URL+resData.soil);
  if(soilDescRes.status === 200) {
    document.getElementById("soil-description").textContent = await soilDescRes.text();
  } else {
    document.getElementById("soil-description").textContent = "";
  }

  const soilImgRes = await fetch(IMG_URL+resData.soil);
  if(soilImgRes.status === 200) {
    document.getElementById("soil-img-el").src = await soilImgRes.text();
  } else {
    document.getElementById("soil-img-el").src = "";
  }

  await fetchFidSpecies(resData.fid);
  treatmentData = await fetchFidTreatment(resData.fid);
  document.getElementById("info-table-page-count").innerHTML = 1;
  document.getElementById("date").innerHTML = null;
  document.getElementById("treatment").innerHTML = null;
  document.getElementById("treatment-type").innerHTML = null;
  setTreatmentHTML(0);
};

const fetchFidSpecies = async (fid) => {
  const res = await fetch(`http://localhost:8080/api/species/fid?fid=${fid}`);
  const resData = await res.json();
  
  const plantDescRes = await fetch(DESCRIPTION_URL+resData[0].plant);
  if(plantDescRes.status === 200) {
    document.getElementById("plant-description").textContent = await plantDescRes.text();
  } else {
    document.getElementById("plant-description").textContent = "";
  }

  const plantImgRes = await fetch(IMG_URL+resData[0].plant);
  if(plantImgRes.status === 200) {
    document.getElementById("plant-img-el").src = await plantImgRes.text();
  } else {
    document.getElementById("plant-img-el").src = "";
  }



  document.getElementById("plant").innerHTML = langChoice === "EN" ? dictionary[resData[0].plant] : resData[0].plant;
  document.getElementById("year").innerHTML = resData[0].year;
  document.getElementById("subspecies").innerHTML =langChoice === "EN" ? dictionary[resData[0].subspecies] : resData[0].subspecies;
};

const fetchFidTreatment = async (fid) => {
  const res = await fetch(`http://localhost:8080/api/treatment/fid?fid=${fid}`);
  const resData = await res.json();
  return resData;
};

const setTreatmentHTML = (count) => {
  document.getElementById("date").innerHTML = treatmentData[count]?.date || "";
  document.getElementById("treatment").innerHTML = treatmentData[count]?.treatment || "";
  document.getElementById("treatment-type").innerHTML = treatmentData[count]?.treatment_type || "";
}

const select = new ol.interaction.Select();
map.addInteraction(select);

fetchInitial();

document.getElementById("back-button").addEventListener("click", () => {
  const buttonCountElement = document.getElementById("info-table-page-count");
  let buttonCount = buttonCountElement.innerHTML;
  if(treatmentData.length===0) return;

  buttonCount = Math.max(Number(buttonCount)-1,1)
  buttonCountElement.innerHTML = Number(buttonCount);
  setTreatmentHTML(Number(buttonCount) - 1);
})

document.getElementById("forward-button").addEventListener("click", () =>  {
  const buttonCountElement = document.getElementById("info-table-page-count");
  if(treatmentData.length===0) return;
  let buttonCount = buttonCountElement.innerHTML;
  buttonCount = Math.min(Number(buttonCount)+1,treatmentData.length)
  

  buttonCountElement.innerHTML = Number(buttonCount);
  setTreatmentHTML(Number(buttonCount)-1);
})

map.on("click", function (e) {
  const position = ol.proj.toLonLat(e.coordinate);
  const [lon, lat] = position;
  fetchPoints(lat, lon);
});

select.on('select', function(e) {

  
  e.selected.forEach(function(feature) {
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
// Remove the checBoxVectorLayer
map.removeLayer(checBoxVectorLayer);

})