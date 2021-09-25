var restorani = [];

$(document).ready(function(){
    $(".podaci").hide();
    $("#komentari").hide();
    $.ajax({
        url : "rest/getRestorani"
    }).then(function(lista) {
        
        for(var i = 0; i< lista.length; i++){
            if(lista[i].status == "Radi") restorani.push(lista[i]);
        }
        
        for(var i = 0; i< lista.length; i++){
            if(lista[i].status == "Ne_radi") restorani.push(lista[i]);
        }
        
         prikaziRestorane(restorani);
    })
    
    $("#searchName").on('keyup', function () {
        filterRestaurants();
    });
    $("#searchType").on('keyup', function () {
        filterRestaurants();
    });
    $("#searchLocation").on('keyup', function () {
        filterRestaurants();
    });
    $("#searchAverageRating").on('keyup', function () {
        filterRestaurants();
    });
    $('#typeSelect').on('change', function () {
        filterRestaurants();
    });
    $('#openedSelect').on('change', function () {
        filterRestaurants();
    });
    $('#sortSelect').on('change', function () {
        filterRestaurants();
    });
    $('#ascDescSelect').on('change', function () {
        filterRestaurants();
    });
    
    
    $("#reset").click(function () {
        $('#searchName').val('');
        $('#searchType').val('');
        $('#searchLocation').val('');
        $('#searchAverageRating').val('');
        $('#typeSelect').val('Svi').change();
        $('#openedSelect').val('Svi').change();
        $('#sortSelect').val('Svi').change();

        prikaziRestorane(restorani);
    });
});

function filterRestaurants() {
    
    var nameInput = $('#searchName').val().toLowerCase();
    var typeInput = $('#searchType').val().toLowerCase();
    var locationInput = $('#searchLocation').val().toLowerCase();
    var ratingInput = $('#searchAverageRating').val().toLowerCase();
    var typeSelect = $('#typeSelect').find(":selected").text().toLowerCase();
    var openedSelect = $('#openedSelect').find(":selected").text().toLowerCase();
    var sortSelect = $('#sortSelect').find(":selected").text().toLowerCase();
    var ascDescSelect = $('#ascDescSelect').find(":selected").text().toLowerCase();
    
    var sviRestorani = restorani;
    var tmpRestorani = [];
    
    //naziv
    for (var i = 0; i < sviRestorani.length; i++) {
        var name = sviRestorani[i].naziv.toLowerCase();
        if (name.includes(nameInput)) tmpRestorani.push(sviRestorani[i]);
    }
    sviRestorani = tmpRestorani;
    tmpRestorani = [];
    
    //tip
    for (var i = 0; i < sviRestorani.length; i++) {
        var type = sviRestorani[i].tipRestorana.toLowerCase();
        if (type.includes(typeInput)) tmpRestorani.push(sviRestorani[i]);
    }
    sviRestorani = tmpRestorani;
    tmpRestorani = [];
    
    //lokacija, korisnik unosi grad
    for (var i = 0; i < sviRestorani.length; i++) {
        var location = sviRestorani[i].lokacija.grad.toLowerCase();
        if (location.includes(locationInput)) tmpRestorani.push(sviRestorani[i]);
    }
    sviRestorani = tmpRestorani;
    tmpRestorani = [];
    
    //prosecna ocena
    for (var i = 0; i < sviRestorani.length; i++) {
        var prosek = sviRestorani[i].prosecnaOcena;
        if (prosek >= ratingInput) tmpRestorani.push(sviRestorani[i]);
    }
    sviRestorani = tmpRestorani;
    tmpRestorani = [];

    //type select
    if (typeSelect == 'tip') tmpRestorani = sviRestorani;
    for (var i = 0; i < sviRestorani.length; i++) {
        if (typeSelect == 'tip') break;
        var typeSelectFromDatabase = sviRestorani[i].tipRestorana.toLowerCase();
        if (typeSelectFromDatabase == typeSelect) tmpRestorani.push(sviRestorani[i]);
    }
    sviRestorani = tmpRestorani;
    tmpRestorani = [];
    
    //opened select
    if (openedSelect == 'svi') tmpRestorani = sviRestorani;
    for (var i = 0; i < sviRestorani.length; i++) {
        if (openedSelect == 'svi') break;
        var jeOtvoren = sviRestorani[i].status;
        if (jeOtvoren == "Radi") tmpRestorani.push(sviRestorani[i]);
    }
    sviRestorani = tmpRestorani;
    tmpRestorani = [];
    
    //sortiram sve restorane po imenu
    if (sortSelect == 'naziv') {
        function compareAscending1(a, b) {
            if (a.naziv < b.naziv) return -1;
            if (a.naziv > b.naziv) return 1;
            return 0;
        }
        function compareDescending1(a, b) {
            if (a.naziv > b.naziv) return -1;
            if (a.naziv < b.naziv) return 1;
            return 0;
        }
        if (ascDescSelect == 'rastuce') {
            sviRestorani.sort(compareAscending1);
        }
        if (ascDescSelect == 'opadajuce') {
            sviRestorani.sort(compareDescending1);
        }
    }
    
    //za lokaciju sortira po gradu
    if (sortSelect == 'lokacija') {
        function compareAscending2(a, b) {
            if (a.lokacija.grad < b.lokacija.grad) return -1;
            if (a.lokacija.grad > b.lokacija.grad) return 1;
            return 0;
        }
        function compareDescending2(a, b) {
            if (a.lokacija.grad > b.lokacija.grad) return -1;
            if (a.lokacija.grad < b.lokacija.grad) return 1;
            return 0;
        }
        if (ascDescSelect == 'rastuce') {
            sviRestorani.sort(compareAscending2);
        }
        if (ascDescSelect == 'opadajuce') {
            sviRestorani.sort(compareDescending2);
        }
    }

    //za lokaciju sortira po gradu
    if (sortSelect == 'rating') {
        function compareAscending2(a, b) {
            if (a.prosecnaOcena < b.prosecnaOcena) return -1;
            if (a.prosecnaOcena > b.prosecnaOcena) return 1;
            return 0;
        }
        function compareDescending2(a, b) {
            if (a.prosecnaOcena > b.prosecnaOcena) return -1;
            if (a.prosecnaOcena < b.prosecnaOcena) return 1;
            return 0;
        }
        if (ascDescSelect == 'rastuce') {
            sviRestorani.sort(compareAscending2);
        }
        if (ascDescSelect == 'opadajuce') {
            sviRestorani.sort(compareDescending2);
        }
    }
    
    
    prikaziRestorane(sviRestorani);
}

function prikaziRestorane(restorani){
    $(".row:last").empty();    
    for(var i = 0; i< restorani.length; i++){ 
        $(".row:last").append('<div class="col-4" onclick="prepoznaj(this)" id="' + restorani[i].naziv + '">' + '<img src="' + restorani[i].logoRestorana + '" >' + '<h3>' + restorani[i].naziv + '</h3> <p>' + restorani[i].tipRestorana + '<br>' + restorani[i].lokacija.ulica + '<br>' + restorani[i].lokacija.grad + ' ' + restorani[i].lokacija.zipCode + '<br>' + "Prosecna ocena: " + restorani[i].prosecnaOcena + '</p> </div>');
    }
}

function prepoznaj(clickedElem){
    var prepoznato = clickedElem.id;
    $(".filters").hide();
    $(".small-container").hide();
    $(".podaci").show();
    $(".podaci").empty();
    $("#komentari").show();
    $.ajax({
        url : "rest/getRestoran?id=" + prepoznato +""
    }).then(function(lista) {
            
        $(".podaci:last").append('<img src="' + lista.logoRestorana +'">');
        $(".podaci:last").append('<h1>' + lista.naziv +'</h1>');
        $(".podaci:last").append('<h3>' + lista.tipRestorana +'</h3>');
        $(".podaci:last").append('<h3>' + lista.status +'</h3>');
        $(".podaci:last").append('<h4>' + lista.lokacija.ulica +'</h4>');
        $(".podaci:last").append('<h4>' + lista.lokacija.grad +'</h4>');
        $(".podaci:last").append('<h4> Ocena: ' + lista.prosecnaOcena+ '</h4>');

        $.ajax({
            url: "rest/getArtikle"
        }).then(function(artikli){
            $(".roww:last").empty();
            for(var x=0; x<artikli.length; x++){
                if(lista.naziv === artikli[x].imeRestorana){
                    $(".roww:last").append('<div class="col-5">' + '<img src="' + artikli[x].slika + '" >' + '<h3>' + artikli[x].naziv + '</h3> <p>Cena: ' + artikli[x].cena + ' rsd <br>Kolicina: ' + artikli[x].kolicina + ' g<br>Opis: ' + artikli[x].opis +'</p> <div class="kolicinaa"><input type="number" class="kolicina" id="' + x + '" size="2"> <button id="add" onclick="dodajArtikalUKorpu(\'' + artikli[x].naziv + ',' + x + ',' + artikli[x].cena + '\')">add</button></div></div>');
                }
            }
            $('.kolicinaa').hide();
            checkSesionKupac();
        })
        $.ajax({
            url: "rest/getSession"
        }).then(function(sesija){
            if(sesija.uloga ==="Administrator"){
                $.ajax({
                    url: "rest/getKomentare"
                }).then(function(komentari){
                    $(".rowwww:last").empty();
                    for(var x=0; x<komentari.length; x++){
                        if(lista.naziv === komentari[x].restoran.naziv){
                            $(".rowwww:last").append('<div class="col-5">' + '<h2>' + komentari[x].tekst + '</h2> <p>Ocena: ' + komentari[x].ocena + '</p> </div>');
                            $(".col-5:last").append('<p> Zahtev: '+ komentari[x].porudzbina.koment +'</p>');
                        }
                    }
                })
            }
            else{
                $.ajax({
                    url: "rest/getKomentare"
                }).then(function(komentari){
                    $(".rowwww:last").empty();
                    for(var x=0; x<komentari.length; x++){
                        if(lista.naziv === komentari[x].restoran.naziv && komentari[x].porudzbina.koment === "potvdjen"){
                            $(".rowwww:last").append('<div class="col-5">' + '<h2>' + komentari[x].tekst + '</h2> <p>Ocena: ' + komentari[x].ocena + '</p> </div>');
                        }
                    }
                })
            }
        })
        
    })
}



function dodajArtikalUKorpu(params) {
    //console.log(params);
    let name = params.split(',')[0];
    let index = params.split(',')[1];
    let quantity = $('#' + index).val();
    let cena = params.split(',')[2];
    //console.log(name + ' ' + quantity);
    if(quantity <= 0){
        alert("Morate uneti kolicinu");
        return;
    }

    $.get({
        url: "rest/addItemToCart?name=" + name + "&quantity=" + quantity + "&flag=no" + "&cena=" + cena,
        success: function () {
            
            
            $('#' + index).val('');
            alert('Artikal je uspjesno dodat u korpu!');

        }
    });
}