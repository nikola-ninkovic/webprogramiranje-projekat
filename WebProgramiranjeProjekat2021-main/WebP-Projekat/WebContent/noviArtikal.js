var imageInBase64;

$(document).ready(function () {

    
    
        $.get({
        url: "rest/loginStatusMenadzer",
        success: function (user) {

            
            $('#formaNoviArtikal').submit(function(event){
            event.preventDefault();

                let naziv = $('input[name="nazivArtikla"]').val();
                let tip = $('select[id="tipArtikla"]').val();
                let imeRestorana = user.restoran.naziv;
                let cena = $('input[name="cenaArtikla"]').val();
                let opis = $('input[name="opisArtikla"]').val();
                let kolicina = $('input[name="kolicinaArtikla"]').val();
                let logo = $('input[name="choose"]').val();
                if(!naziv || !cena || !tip || !logo){
                    return alert("Unesite parametre.");
                }
                $.post({
                    url: 'rest/noviArtikal',
                    data: JSON.stringify({naziv: naziv, cena: cena, tip: tip, imeRestorana: imeRestorana, kolicina: kolicina, opis: opis, slika: logo}),
                    contentType: 'application/json',
                    success: function(message){

                        $.post({
                            url: "rest/saveArtikalImage",
                            data: imageInBase64,
                            contentType: "application/json",
                            succes: function(data2){
                                console.log(imageInBase64);
                            }
                        })

                        window.location.href = "http://localhost:8080/PocetniREST/index.html";

                    },
                    error: function(message){
                        $('#error').text(message.responseText);
                        $('#error').show();
                    }
            }); 

        });
            
            
        }
    });


});


function encodeImageFileAsURL(element) {
    var file = element.files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        //console.log('RESULT', reader.result)
        imageInBase64 = reader.result;
        //console.log(imageInBase64);
    }
    reader.readAsDataURL(file);
}