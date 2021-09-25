var sviArtikli = [];

$(document).ready(function(){

    $.ajax({
        url : "rest/getKorpa"
    }).then(function(korpa) {

        $.ajax({
            url : "rest/getArtikle"
        }).then(function(artikli) {
           var ukorpi = korpa.artikalIkolicina
           for(var m in ukorpi){
                for(var x=0; x<artikli.length; x++){
                    if(m == (artikli[x].naziv)){
                        $(".roww:last").append('<div class="col-5">' + '<img src="' + artikli[x].slika + '" >' + '<h3>' + artikli[x].naziv + '</h3> <p>Cena: ' + artikli[x].cena + ' rsd <br>Kolicina: ' + artikli[x].kolicina + ' g<br>Opis: ' + artikli[x].opis +'</p> <div class="kolicinaa"><input onchange="promeniKolicinu(\'' + artikli[x].naziv + ',' + x + ',' + artikli[x].cena + ',' + ukorpi[m] +'\')" type="number" class="kolicina" id="' + x + '" size="2" value="'+ ukorpi[m] +'"> <button id="remove" onclick="ukloniArtikalIzKorpe(\'' + artikli[x].naziv + ',' + x + ',' + artikli[x].cena + '\')">del</button> </div></div>');
                        sviArtikli.push(artikli[x]);
                        break;
                    }
                }
           }
           $.ajax({
            url : "rest/loginStatusKupac"
            }).then(function(user) {
        
                if (user != undefined) {
                    if(user.tipKupca){
                        $(".naslov:last").append('<h1 style="margin-bottom:10px;"> Ukupna cena: '+ korpa.cena +'</h1><h2> Popust: '+ user.tipKupca.popust +'</h2><p>(za narudzbine od min 300 rsd)  </p></br><button onclick="kreirajPorudzbinu('+ korpa.cena +')">Potvrdi</button>')
                    }
                    else{
                        $(".naslov:last").append('<h1 style="margin-bottom:10px;"> Ukupna cena: '+ korpa.cena +'</h1><h2> Popust: 0</h2><p>(za narudzbine od min 300 rsd)  </p></br><button onclick="kreirajPorudzbinu('+ korpa.cena +')">Potvrdi</button>')
                    }
                    
                }
            });
        })
        
    })
});


function ukloniArtikalIzKorpe(params){
    let name = params.split(',')[0];
    let index = params.split(',')[1];
    let quantity = $('#' + index).val();
    let cena = params.split(',')[2];
    
    
    $.get({
        url: "rest/removeItemFromCart?name=" + name + "&quantity=" + quantity + "&cena=" + cena,
        success: function () {
            alert('Artikal je uspjesno uklonjen iz korpe!');
            window.location.href = "http://localhost:8080/PocetniREST/korpa.html";
        }
    });

}


function promeniKolicinu(params){
    let name = params.split(',')[0];
    let index = params.split(',')[1];
    let quantity = $('#' + index).val();
    let cena = params.split(',')[2];
    let oldQuantity = params.split(',')[3];

    $.get({
        url: "rest/changeItemInCart?name=" + name + "&quantity=" + quantity + "&cena=" + cena + "&oldQuantity=" + oldQuantity,
        success: function () {
            window.location.href = "http://localhost:8080/PocetniREST/korpa.html";
        }
    });

    if(quantity<=0)
        ukloniArtikalIzKorpe(params);

}

function kreirajPorudzbinu(ukupnacena){
    
    let data = {
        'artikli': sviArtikli,
        'cena': ukupnacena,
        'datumPorudzbine': new Date()
    }
    
     $.post({
        url: "rest/createOrder",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function () {
            alert('Uspjesno kreirana narudzbina!');
            window.location.href = "http://localhost:8080/PocetniREST/korpa.html";
        }
    });


}