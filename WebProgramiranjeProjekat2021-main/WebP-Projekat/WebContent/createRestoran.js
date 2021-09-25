$(document).ready(function(){
    $('#formaRestorana').submit(function(event){
        event.preventDefault();
        let naziv = $('input[name="naziv"]').val();
        let tip = $('select[id="tipRestorana"]').val();
        let status = "Radi";
        
        let duzina = $('input[name="duzina"]').val();
        let sirina = $('input[name="sirina"]').val();
        let grad = $('input[name="grad"]').val();
        let ulica = $('input[name="ulica"]').val();
        let zipCode = $('input[name="zipCode"]').val();
        
        let lokacija = {duzina, sirina, ulica, grad, zipCode};
        
        let logo = $('input[name="choose"]').val();
        let menadzer = $('select[id="menadzer"]').val();

        if(menadzer === "Nema slobodnog menadzera"){
            return alert("Kreirajte novog menadzera.")
        }
        else if(!naziv || !tip || !lokacija || !logo){
            return alert("Unesite parametre.");
        }

        $.post({
            url: 'rest/createRestoran',
            data: JSON.stringify({naziv: naziv, tipRestorana: tip, status: status, lokacija: lokacija, logoRestorana: logo}),
            contentType: 'application/json',
            success: function(message){
                
                //rest za dodavanje restorana menadzeru
                $.post({
                    url: "rest/setManagersRestaurant", 
                    data: menadzer,
                    contentType: "application/json",
                });
                
                $.post({
                    url: "rest/saveImage",
                    data: imageInBase64,
                    contentType: "application/json",
                    succes: function(data2){
                        console.log(imageInBase64);
                    }
                })

                window.location.href = "http://localhost:8080/PocetniREST/profil.html";
                
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });  
    });
});

function encodeImageFileAsURL(element){
    var file = element.files[0];
    var reader = new FileReader();
    reader.onloadend = function(){
        imageInBase64 = reader.result;
    }
    reader.readAsDataURL(file);
}