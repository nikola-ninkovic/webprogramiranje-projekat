var svePorudzbine = [];

$(document).ready(function(){

    $.ajax({
        url : "rest/loginStatusDostavljac"
    }).then(function(user) {
        $.ajax({
            url : "rest/getSvePorudzbine"
        }).then(function(listaPorudzbina) {
            for(var x=0; x<listaPorudzbina.length; x++){
                if( listaPorudzbina[x].statusPorudzbine === "Ceka_dostavljaca" ){
                    $(".rowwww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '</p> </div>');
                    svePorudzbine.push(listaPorudzbina[x]);
                    if(!listaPorudzbina[x].dostavljac)
                        $(".col-5:last").append('<button style="width:200px" onclick="posaljiZahtev(' + listaPorudzbina[x].id + ')">Posalji zahtev</button>');
                }
            }
           
        })        
    });

    $('#mojeDostave').click(function(){
        $('.rowwww').empty();
        izlistajMojePorudzbine();
    })

    $('#ponude').click(function(){
        $('.rowwww').empty();
        izlistajSvePorudzbine(svePorudzbine);
    })

    $('#nedostavljene').click(function(){
        $('.rowwww').empty();
        izlistajMojeNedostavljenePorudzbine();
    })

    $("#searchNaziv").on('keyup', function () {
        filterPorudzbine();
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
    $('#typeSelecttt').on('change', function () {
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
        $('#searchNaziv').val('');
        $('#searchOd').val('');
        $('#searchDo').val('');
        $('#do').val('');
        $('#od').val('');
        $('#typeSelecttt').val('Tip').change();
        $('#statusPorudzbine').val('Svi').change();
        $('#sortSelecttt').val('Svi').change();

        izlistajMojePorudzbine(svePorudzbine);
    });
});

function posaljiZahtev(id) {
    
    $.post({
    url: "rest/posaljiZahtev",
    data: JSON.stringify({ id: id}),
    contentType: "application/json",
    success: function () {
        window.location.href = "http://localhost:8080/PocetniREST/dostavljacPorudzbine.html";
    }
    });
}
function izlistajMojePorudzbine(){
    $.ajax({
        url : "rest/loginStatusDostavljac"
    }).then(function(user) {
        svePorudzbine = [];
        let listaPorudzbina = user.listaPorudzbinaZaDostaviti;
        for(var x=0; x<listaPorudzbina.length; x++){
            if( listaPorudzbina[x].statusPorudzbine === "U_transportu" ){
                $(".rowwww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '</p> </div>');
                $(".col-5:last").append('<button style="width:200px" onclick="dostavi(' + listaPorudzbina[x].id + ')">Dostavljena</button>');
                svePorudzbine.push(listaPorudzbina[x]);
            }
            if( listaPorudzbina[x].statusPorudzbine === "Dostavljena" ){
                $(".rowwww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '</p> </div>');
                svePorudzbine.push(listaPorudzbina[x]);
            }
        }

    });
}
function izlistajMojeNedostavljenePorudzbine(){
    $.ajax({
        url : "rest/loginStatusDostavljac"
    }).then(function(user) {
        svePorudzbine = [];
        let listaPorudzbina = user.listaPorudzbinaZaDostaviti;
        for(var x=0; x<listaPorudzbina.length; x++){
            if( listaPorudzbina[x].statusPorudzbine === "U_transportu" ){
                $(".rowwww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '</p> </div>');
                svePorudzbine.push(listaPorudzbina[x]);
            }
        }

    });
}
function izlistajSvePorudzbine(listaPorudzbina){
    $(".rowwww").empty();
    for(var x=0; x<listaPorudzbina.length; x++){
        if( listaPorudzbina[x].statusPorudzbine === "Ceka_dostavljaca" ){
            $(".rowwww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '</p> </div>');
            if(!listaPorudzbina[x].dostavljac)
                $(".col-5:last").append('<button style="width:200px" onclick="posaljiZahtev(' + listaPorudzbina[x].id + ')">Posalji zahtev</button>');
        }
    }
}
function dostavi(id) {
    
    $.post({
    url: "rest/dostavi",
    data: JSON.stringify({ id: id}),
    contentType: "application/json",
    success: function () {
        window.location.href = "http://localhost:8080/PocetniREST/dostavljacPorudzbine.html";
    }
    });
}

function filterPorudzbine(){
    var nazivInput = $('#searchNaziv').val().toLowerCase();
    var searchOdInput = $('#searchOd').val().toLowerCase();
    var searchDoInput = $('#searchDo').val().toLowerCase();
    var odInput = $('#od').val().toLowerCase();
    var doInput = $('#do').val().toLowerCase();
    var typeSelect = $('#typeSelecttt').find(":selected").text().toLowerCase();
    var statusPorudzbineSelect = $('#statusPorudzbine').find(":selected").text();
    var sortSelect = $('#sortSelecttt').find(":selected").text();
    var ascDescSelect = $('#ascDesc').find(":selected").text().toLowerCase();

    var SVEPorudzbine = svePorudzbine;
    var tmpPorudzbine = [];

    //naziv restorana
    for(var i=0; i<SVEPorudzbine.length; i++){
        var naziv = SVEPorudzbine[i].restoran.naziv.toLowerCase();
        if (naziv.includes(nazivInput)) tmpPorudzbine.push(SVEPorudzbine[i]);
    }
    SVEPorudzbine = tmpPorudzbine;
    tmpPorudzbine = [];

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

    //tip restorana
    if (typeSelect === 'tip') tmpPorudzbine = SVEPorudzbine;
    for (var i = 0; i < SVEPorudzbine.length; i++) {
        if (typeSelect == 'tip') break;
        var typeSelectFromDatabase = SVEPorudzbine[i].restoran.tipRestorana.toLowerCase();
        if (typeSelectFromDatabase == typeSelect) tmpPorudzbine.push(SVEPorudzbine[i]);
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


    //sortiranje po nazivu
    if(sortSelect == 'Naziv restorana'){
        function compareAscending1(a,b){
            if(a.restoran.naziv < b.restoran.naziv) return -1;
            if(a.restoran.naziv > b.restoran.naziv) return 1;
            return 0;
        }
        function compareDescending1(a,b){
            if(a.restoran.naziv > b.restoran.naziv) return -1;
            if(a.restoran.naziv < b.restoran.naziv) return 1;
            return 0;
        }
        if(ascDescSelect == 'rastuce'){
            SVEPorudzbine.sort(compareAscending1);
        }
        if(ascDescSelect == 'opadajuce'){
            SVEPorudzbine.sort(compareDescending1);
        }
    }

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

    izlistajSvePorudzbine(SVEPorudzbine);
}