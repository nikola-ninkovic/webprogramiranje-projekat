var svePorudzbine = [];
var all;
var glava;
$(document).ready(function(){

    $.ajax({
        url : "rest/loginStatusMenadzer"
    }).then(function(user) {
        glava = user.restoran.naziv;
        $.ajax({
            url : "rest/getSvePorudzbine"
        }).then(function(listaPorudzbina) {
            all = listaPorudzbina;
            for(var x=0; x<listaPorudzbina.length; x++){
                svePorudzbine.push(listaPorudzbina[x]);
                if(listaPorudzbina[x].restoran.naziv === user.restoran.naziv){
                    $(".rowwww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum: ' + listaPorudzbina[x].datumPorudzbine + '<br>Korisnik: ' + listaPorudzbina[x].punoImePrezimeKupca + '</p> </div>');
                }
                
                if( listaPorudzbina[x].statusPorudzbine === "Obrada" ){
                    $(".col-5:last").append('<button onclick="promeniUPripremi(' + listaPorudzbina[x].id + ')">U pripremi</button>');
                }

                if( listaPorudzbina[x].statusPorudzbine === "U_pripremi" ){
                    $(".col-5:last").append('<button onclick="promeniUCekaDostavljaca(' + listaPorudzbina[x].id + ')">Ceka dostavljaca</button>');
                }

                if( listaPorudzbina[x].statusPorudzbine === "Ceka_dostavljaca" ){
                    if(listaPorudzbina[x].dostavljac){
                        $(".col-5:last").append('<p style="margin-top: 15px;">'+ listaPorudzbina[x].dostavljac + ' Dostavljac</p>');
                        $(".col-5:last").append('<button onclick="Potvrdi(' + listaPorudzbina[x].id + ')">Potvrdi</button><button onclick="Otkazi(' + listaPorudzbina[x].id + ')">Otkazi</button>');
                    }
                }

            }
           
        })  
    });
        
    $("#searchOd").on('keyup', function () {
            filterPorudzbine();
    });
    $("#searchDo").on('keyup', function () {
            filterPorudzbine();
    });
    $("#do").on('change', function () {
            filterPorudzbine();
    });
    $("#od").on('change', function () {
            filterPorudzbine();
    });
    $('#statusPorudzbine').on('change', function () {
            filterPorudzbine();
    });
    $('#sortSelecttt').on('change', function () {
            filterPorudzbine();
    });
    $('#ascDesc').on('change', function () {
            filterPorudzbine();
    });
    $("#resett").click(function () {
        $('#searchOd').val('');
        $('#searchDo').val('');
        $('#do').val('');
        $('#od').val('');
        $('#statusPorudzbine').val('Svi').change();
        $('#sortSelecttt').val('Svi').change();

        izlistajPorudzbine(all);
    });


});

function izlistajPorudzbine(listaPorudzbina){
    $(".rowwww").empty();
    for(var x=0; x<listaPorudzbina.length; x++){
        if(listaPorudzbina[x].restoran.naziv === glava){
            $(".rowwww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '<br>Korisnik: ' + listaPorudzbina[x].punoImePrezimeKupca + '</p> </div>');
        }
        
        if( listaPorudzbina[x].statusPorudzbine === "Obrada" ){
            $(".col-5:last").append('<button onclick="promeniUPripremi(' + listaPorudzbina[x].id + ')">U pripremi</button>');
        }

        if( listaPorudzbina[x].statusPorudzbine === "U_pripremi" ){
            $(".col-5:last").append('<button onclick="promeniUCekaDostavljaca(' + listaPorudzbina[x].id + ')">Ceka dostavljaca</button>');
        }

        if( listaPorudzbina[x].statusPorudzbine === "Ceka_dostavljaca" ){
            if(listaPorudzbina[x].dostavljac){
                $(".col-5:last").append('<p style="margin-top: 15px;">'+ listaPorudzbina[x].dostavljac + ' Dostavljac</p>');
                $(".col-5:last").append('<button onclick="Potvrdi(' + listaPorudzbina[x].id + ')">Potvrdi</button><button onclick="Otkazi(' + listaPorudzbina[x].id + ')">Otkazi</button>');
            }
        }

    }
}

function promeniUPripremi(id) {
    
    $.post({
    url: "rest/promeniUPripremi",
    data: JSON.stringify({ id: id}),
    contentType: "application/json",
    success: function () {
        window.location.href = "http://localhost:8080/PocetniREST/menadzerPorudzbine.html";
    }
    });
}
function promeniUCekaDostavljaca(id) {
    
    $.post({
    url: "rest/promeniUCekaDostavljaca",
    data: JSON.stringify({ id: id}),
    contentType: "application/json",
    success: function () {
        window.location.href = "http://localhost:8080/PocetniREST/menadzerPorudzbine.html";
    }
    });
}
function Otkazi(id) {
    
    $.post({
    url: "rest/otkazi",
    data: JSON.stringify({ id: id}),
    contentType: "application/json",
    success: function () {
        window.location.href = "http://localhost:8080/PocetniREST/menadzerPorudzbine.html";
    }
    });
}
function Potvrdi(id) {
    
    $.post({
    url: "rest/potvrdi",
    data: JSON.stringify({ id: id}),
    contentType: "application/json",
    success: function () {
        window.location.href = "http://localhost:8080/PocetniREST/menadzerPorudzbine.html";
    }
    });
}

function filterPorudzbine(){
    var searchOdInput = $('#searchOd').val().toLowerCase();
    var searchDoInput = $('#searchDo').val().toLowerCase();
    var odInput = $('#od').val().toLowerCase();
    var doInput = $('#do').val().toLowerCase();
    var statusPorudzbineSelect = $('#statusPorudzbine').find(":selected").text();
    var sortSelect = $('#sortSelecttt').find(":selected").text();
    var ascDescSelect = $('#ascDesc').find(":selected").text().toLowerCase();

    var SVEPorudzbine = svePorudzbine;
    var tmpPorudzbine = [];

    //cena
    for(var i=0; i<SVEPorudzbine.length; i++){
        var od = SVEPorudzbine[i].cena;
        if(od>=searchOdInput) tmpPorudzbine.push(SVEPorudzbine[i]);
    }
    SVEPorudzbine = tmpPorudzbine;
    tmpPorudzbine = [];

    //cena
    for(var i=0; i<SVEPorudzbine.length; i++){
            var dos = SVEPorudzbine[i].cena;
            if(searchDoInput==='') tmpPorudzbine.push(SVEPorudzbine[i]);
            if(dos<=searchDoInput) tmpPorudzbine.push(SVEPorudzbine[i]);
    }
    SVEPorudzbine = tmpPorudzbine;
    tmpPorudzbine = [];

    //datum
    for(var i=0; i<SVEPorudzbine.length; i++){
            var od = SVEPorudzbine[i].datumPorudzbine;
            if(od>=odInput) tmpPorudzbine.push(SVEPorudzbine[i]);
    }
    SVEPorudzbine = tmpPorudzbine;
    tmpPorudzbine = [];

    //datum
    for(var i=0; i<SVEPorudzbine.length; i++){
            var dos = SVEPorudzbine[i].datumPorudzbine;
            if(doInput==='') tmpPorudzbine.push(SVEPorudzbine[i]);
            if(dos<=doInput) tmpPorudzbine.push(SVEPorudzbine[i]);
    }
    SVEPorudzbine = tmpPorudzbine;
    tmpPorudzbine = [];

    //status porudzbine
    if (statusPorudzbineSelect == 'Status porudzbine') tmpPorudzbine = SVEPorudzbine;
    for (var i = 0; i < SVEPorudzbine.length; i++) {
            if (statusPorudzbineSelect == 'Status porudzbine') break;
            var uloga = SVEPorudzbine[i].statusPorudzbine;
            if (uloga == statusPorudzbineSelect.replace(' ','_')) tmpPorudzbine.push(SVEPorudzbine[i]);
    }
    SVEPorudzbine = tmpPorudzbine;
    tmpPorudzbine = [];

    //sortiranje po ceni
    if(sortSelect == 'Cena porudzbina'){
        function compareAscending1(a,b){
            if(a.cena < b.cena) return -1;
            if(a.cena > b.cena) return 1;
            return 0;
        }
        function compareDescending1(a,b){
            if(a.cena > b.cena) return -1;
            if(a.cena < b.cena) return 1;
            return 0;
        }
        if(ascDescSelect == 'rastuce'){
            SVEPorudzbine.sort(compareAscending1);
        }
        if(ascDescSelect == 'opadajuce'){
            SVEPorudzbine.sort(compareDescending1);
        }
    }

    //sortiranje po datumu
    if(sortSelect == 'Datum porudzbine'){
        function compareAscending1(a,b){
            if(a.datumPorudzbine < b.datumPorudzbine) return -1;
            if(a.datumPorudzbine > b.datumPorudzbine) return 1;
            return 0;
        }
        function compareDescending1(a,b){
            if(a.datumPorudzbine > b.datumPorudzbine) return -1;
            if(a.datumPorudzbine < b.datumPorudzbine) return 1;
            return 0;
        }
        if(ascDescSelect == 'rastuce'){
            SVEPorudzbine.sort(compareAscending1);
        }
        if(ascDescSelect == 'opadajuce'){
            SVEPorudzbine.sort(compareDescending1);
        }
    }

    izlistajPorudzbine(SVEPorudzbine);
}