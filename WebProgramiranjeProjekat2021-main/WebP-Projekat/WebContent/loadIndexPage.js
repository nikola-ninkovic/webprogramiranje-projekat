var korisnici = [];
var kupci;
$(document).ready(function() {
    
    
    $('#korpaPanel').hide();
    $('#kupacPorudzbine').hide();
	$('#profilPanel').hide(); 
    $('#dostavljacPorudzbine').hide();
	$('#logout').hide();
	$('#mojRestoran').hide();
    $('#menadzerPorudzbine').hide();
    $('#noviArtikal').hide();
	$('.nav-buttons-admin').hide();
	$('.box2').hide();
	$('.box3').hide();
	$('.box4').hide();
	$('.box6').hide();
	$('.filterss').hide();
    
	checkSesionKupac();
    checkSesionAdministrator();
	checkSesionMenadzer();
	checkSesionDostavljac();
    
    $('#logout').click(function () {
        logoutUser();
    });
	
	$.ajax({
		url : "rest/getKupci"
	}).then(function(lista) {
		kupci = lista;
	});
});


function checkSesionKupac() {
	$.ajax({
		url : "rest/loginStatusKupac"
	}).then(function(user) {

		if (user != undefined) {
			$('#login').hide();
			$('#mojRestoran').hide();
            $('#menadzerPorudzbine').hide();
            $('#noviArtikal').hide();
			$('#profilPanel').text(user.korisnickoIme);
			$('#profilPanel').show();
            $('#dostavljacPorudzbine').hide();
			$('#logout').show();
            $('#korpaPanel').show();
            $('#kupacPorudzbine').show();
			$('.kolicinaa').show();
            
            $('input[name="ime"]').val(user.ime);
            $('input[name="prezime"]').val(user.prezime);
			$('input[name="korisnickoIme"]').val(user.korisnickoIme);
            $('input[name="lozinka"]').val(user.lozinka);
            $('input[name="datumRodjenja"]').val(user.datumRodjenja);
			$('select[name="pol"]').val(user.pol);
            
		}
	});
}

function checkSesionDostavljac() {
	$.ajax({
		url : "rest/loginStatusDostavljac"
	}).then(function(user) {

		if (user != undefined) {
			$('#login').hide();
			$('#mojRestoran').hide();
            $('#menadzerPorudzbine').hide();
            $('#noviArtikal').hide();
			$('#profilPanel').text(user.korisnickoIme);
			$('#profilPanel').show();
            $('#dostavljacPorudzbine').show();
			$('#logout').show();
            $('#korpaPanel').hide();
            $('#kupacPorudzbine').hide();
            
            $('input[name="ime"]').val(user.ime);
            $('input[name="prezime"]').val(user.prezime);
			$('input[name="korisnickoIme"]').val(user.korisnickoIme);
            $('input[name="lozinka"]').val(user.lozinka);
            $('input[name="datumRodjenja"]').val(user.datumRodjenja);
			$('select[name="pol"]').val(user.pol);
            
		}
	});
}

function checkSesionMenadzer() {
	$.ajax({
		url : "rest/loginStatusMenadzer"
	}).then(function(user) {

		if (user != undefined) {
			$('#login').hide();
			$('#mojRestoran').show();
            $('#menadzerPorudzbine').show();
            $('#noviArtikal').show();
			$('#profilPanel').text(user.korisnickoIme);
			$('#profilPanel').show();
            $('#dostavljacPorudzbine').hide();
			$('#logout').show();
            $('#korpaPanel').hide();
            $('#kupacPorudzbine').hide();
            
            $('input[name="ime"]').val(user.ime);
            $('input[name="prezime"]').val(user.prezime);
			$('input[name="korisnickoIme"]').val(user.korisnickoIme);
            $('input[name="lozinka"]').val(user.lozinka);
            $('input[name="datumRodjenja"]').val(user.datumRodjenja);
			$('select[name="pol"]').val(user.pol);
		}
	});
}

function checkSesionAdministrator(){
    $.ajax({
		url : "rest/loginStatusAdministrator"
	}).then(function(user) {

		if (user != undefined) {
			$('#login').hide();
			$('#mojRestoran').hide();
            $('#menadzerPorudzbine').hide();
            $('#noviArtikal').hide();
			$('#profilPanel').text(user.korisnickoIme);
			$('#profilPanel').show();
            $('#dostavljacPorudzbine').hide();
			$('#logout').show();
			$('.nav-buttons-admin').show();
			$('.loginbox').css("left", "55%");

			$('input[name="ime"]').val(user.ime);
            $('input[name="prezime"]').val(user.prezime);
			$('input[name="korisnickoIme"]').val(user.korisnickoIme);
            $('input[name="lozinka"]').val(user.lozinka);
            $('input[name="datumRodjenja"]').val(user.datumRodjenja);
			$('select[name="pol"]').val(user.pol);
			
            
            
            $('#sumnjiviKorisnici').click(function () {
				korisnici = [];
				$('.box1').hide();
				$('.box3').hide();
				$('.box2').show();
				$('.box4').hide();
				$('.box6').hide();
				$('.filterss').hide();
				$('.box2 ul').empty();
				$.ajax({
					url : "rest/getKupci"
				}).then(function(lista) {
					for(var i = 0; i< lista.length; i++){
                        
                        if(lista[i].sumnjiv){
						$("#box2ul:last").append('<li>' + lista[i].korisnickoIme + ' - ' + lista[i].ime + ' - ' + lista[i].prezime + ' - ' + lista[i].uloga  + '<button class="sumnjivButon" style=" margin-left:20px;" onclick="blokirajKupca(\'' + lista[i].korisnickoIme + '\')"> Block </button>' + '</li>');
						korisnici.push(lista[i]);
                        }
					}
				})
			});
            
            
            // -----------
			$('#korisnici').click(function () {
				korisnici = [];
				$('.box1').hide();
				$('.box3').hide();
				$('.box2').show();
				$('.box4').hide();
				$('.box6').hide();
				$('.filterss').show();
				$('.box2 ul').empty();
				$.ajax({
					url : "rest/getKorisnici"
				}).then(function(lista) {
					for(var i = 0; i< lista.length; i++){
						$("#box2ul:last").append('<li>' + lista[i].korisnickoIme + ' - ' + lista[i].ime + ' - ' + lista[i].prezime + ' - ' + lista[i].uloga + '<button class="sumnjivButon" style=" margin-left:20px;" onclick="blokirajKorisnika(\'' + lista[i].korisnickoIme + '\')"> Block </button>' + '</li>');
						korisnici.push(lista[i]);
					}
				})

				$("#searchIme").on('keyup', function () {
					filterKorisnike();
				});
				$("#searchPrezime").on('keyup', function () {
					filterKorisnike();
				});
				$("#searchKorisnickoIme").on('keyup', function () {
					filterKorisnike();
				});
				$('#typeSelectt').on('change', function () {
					filterKorisnike();
				});
				$('#ulogaSelect').on('change', function () {
					filterKorisnike();
				});
				$('#sortSelectt').on('change', function () {
					filterKorisnike();
				});
				$('#ascDesc').on('change', function () {
					filterKorisnike();
				});
				$("#resett").click(function () {
					$('#searchIme').val('');
					$('#searchPrezime').val('');
					$('#searchKorisnickoIme').val('');
					$('#typeSelectt').val('Svi').change();
					$('#ulogaSelect').val('Svi').change();
					$('#sortSelectt').val('Svi').change();
			
					prikaziKorisnike(korisnici);
				});
			});
			$('#profilAdmina').click(function () {
				$('.box1').show();
				$('.box2').hide();
				$('.box3').hide();
				$('.box4').hide();
				$('.box6').hide();
				$('.filterss').hide();
			});
			$('#kreirajMenadzera').click(function(){
				$('.box1').hide();
				$('.box2').hide();
				$('.box3').show();
				$('.box4').hide();
				$('.box6').hide();
				$('.filterss').hide();
			})
			$('#kreirajRestoran').click(function(){
				$('.box1').hide();
				$('.box2').hide();
				$('.box3').hide();
				$('.box4').show();
				$('.box6').hide();
				$('.filterss').hide();
				$.ajax({
					url : "rest/getSlobodniMenadzeri",
					//Nece da radi :(
					error: function(){
						$("#menadzer:last").append('<option> Nema slobodnog menadzera </option>');
					}
				}).then(function(lista) {
					for(var i = 0; i< lista.length; i++){
						$("#menadzer:last").append('<option value=' + lista[i].korisnickoIme +'>' + lista[i].ime + ' - ' + lista[i].prezime + '</option>');
					}
				})
			})
			$('#kreirajDostavljaca').click(function(){
				$('.box1').hide();
				$('.box2').hide();
				$('.box3').hide();
				$('.box4').hide();
				$('.box6').show();
				$('.filterss').hide();
			})
		}
	});
}


function logoutUser() {
    $.ajax({
		url: "rest/logout",

		success : function() {
			refresh();
		},
		
		error : function(message) {
			alert(message.responseText);
		}
    });
}

function refresh() {
	window.location.href = "http://localhost:8080/PocetniREST/index.html";
}

function prikaziKorisnike(korisnici){
	$('.box2 ul').empty();
	for(var i = 0; i< korisnici.length; i++){
		$("#box2ul:last").append('<li>' + korisnici[i].korisnickoIme + ' - ' + korisnici[i].ime + ' - ' + korisnici[i].prezime + ' - ' + korisnici[i].uloga + '</li>');
	}
}

function filterKorisnike(){

	var imeInput = $('#searchIme').val().toLowerCase();
	var prezimeInput = $('#searchPrezime').val().toLowerCase();
	var korisnickoImeInput = $('#searchKorisnickoIme').val().toLowerCase();
	var typeSelect = $('#typeSelectt').find(":selected").text().toLowerCase();
	var ulogaSelect = $('#ulogaSelect').find(":selected").text();
	var sortSelect = $('#sortSelectt').find(":selected").text();
	var ascDescSelect = $('#ascDesc').find(":selected").text().toLowerCase();

	var sviKorisnici = korisnici;
	var tmpKorisnici = [];

	//ime
	for(var i=0; i<sviKorisnici.length; i++){
		var ime = sviKorisnici[i].ime.toLowerCase();
		if (ime.includes(imeInput)) tmpKorisnici.push(sviKorisnici[i]);
	}
	sviKorisnici = tmpKorisnici;
	tmpKorisnici = [];

	//prezime
	for(var i=0; i<sviKorisnici.length; i++){
		var prezime = sviKorisnici[i].prezime.toLowerCase();
		if(prezime.includes(prezimeInput)) tmpKorisnici.push(sviKorisnici[i]);
	}
	sviKorisnici = tmpKorisnici;
	tmpKorisnici = [];

	//korisnicko ime
	for(var i=0; i<sviKorisnici.length; i++){
		var korisnickoIme = sviKorisnici[i].korisnickoIme.toLowerCase();
		if(korisnickoIme.includes(korisnickoImeInput)) tmpKorisnici.push(sviKorisnici[i]);
	}
	sviKorisnici = tmpKorisnici;
	tmpKorisnici = [];

	//tip kupca
	if (typeSelect == 'tip') tmpKorisnici = sviKorisnici;
    for (var i = 0; i < sviKorisnici.length; i++) {
        if (typeSelectt == 'tip') break;
		if(sviKorisnici[i].tipKupca){
			var typeSelectFromDatabase = sviKorisnici[i].tipKupca.imeTip.toLowerCase();
			if (typeSelectFromDatabase == typeSelect) tmpKorisnici.push(sviKorisnici[i]);
		}
    }
    sviKorisnici = tmpKorisnici;
	tmpKorisnici = [];

	//uloga select
	if (ulogaSelect == 'Sortiraj') tmpKorisnici = sviKorisnici;
    for (var i = 0; i < sviKorisnici.length; i++) {
        if (ulogaSelect == 'Sortiraj') break;
        var uloga = sviKorisnici[i].uloga;
        if (uloga == ulogaSelect) tmpKorisnici.push(sviKorisnici[i]);
    }
    sviKorisnici = tmpKorisnici;
	tmpKorisnici = [];


	//sortiranje po imenu
	if(sortSelect == 'Ime'){
		function compareAscending1(a,b){
			if(a.ime < b.ime) return -1;
			if(a.ime > b.ime) return 1;
			return 0;
		}
		function compareDescending1(a,b){
			if(a.ime > b.ime) return -1;
			if(a.ime < b.ime) return 1;
			return 0;
		}
		if(ascDescSelect == 'rastuce'){
			sviKorisnici.sort(compareAscending1);
		}
		if(ascDescSelect == 'opadajuce'){
			sviKorisnici.sort(compareDescending1);
		}
	}

	//sortiranje po prezimenu
	if(sortSelect == 'Prezime'){
		function compareAscending1(a,b){
			if(a.prezime < b.prezime) return -1;
			if(a.prezime > b.prezime) return 1;
			return 0;
		}
		function compareDescending1(a,b){
			if(a.prezime > b.prezime) return -1;
			if(a.prezime < b.prezime) return 1;
			return 0;
		}
		if(ascDescSelect == 'rastuce'){
			sviKorisnici.sort(compareAscending1);
		}
		if(ascDescSelect == 'opadajuce'){
			sviKorisnici.sort(compareDescending1);
		}
	}

	//sortiranje po korisnickom imenu
	if(sortSelect == 'Korisnicko ime'){
		function compareAscending1(a,b){
			if(a.korisnickoIme < b.korisnickoIme) return -1;
			if(a.korisnickoIme > b.korisnickoIme) return 1;
			return 0;
		}
		function compareDescending1(a,b){
			if(a.korisnickoIme > b.korisnickoIme) return -1;
			if(a.korisnickoIme < b.korisnickoIme) return 1;
			return 0;
		}
		if(ascDescSelect == 'rastuce'){
			sviKorisnici.sort(compareAscending1);
		}
		if(ascDescSelect == 'opadajuce'){
			sviKorisnici.sort(compareDescending1);
		}
	}

	//sortiranje po broj sakupljenih bodova
	if(sortSelect == 'Broj sakupljenih bodova'){
		function compareAscending1(a,b){
			if(a.brojSakupljenihBodova < b.brojSakupljenihBodova) return -1;
			if(a.brojSakupljenihBodova > b.brojSakupljenihBodova) return 1;
			return 0;
		}
		function compareDescending1(a,b){
			if(a.brojSakupljenihBodova > b.brojSakupljenihBodova) return -1;
			if(a.brojSakupljenihBodova < b.brojSakupljenihBodova) return 1;
			return 0;
		}
		if(ascDescSelect == 'rastuce'){
			kupci.sort(compareAscending1);
		}
		if(ascDescSelect == 'opadajuce'){
			kupci.sort(compareDescending1);
		}
		return prikaziKorisnike(kupci);
	}

	prikaziKorisnike(sviKorisnici);
}




function blokirajKorisnika(params){
    
    let username = params;
    
    //console.log(username);
    
    $.get({
        url: "rest/blokirajKorisnika?username=" + username,
        success: function () {
            alert('Korisnik je uspjesno blokiran!');
            window.location.href = "http://localhost:8080/PocetniREST/profil.html";
        }
    });

}

function blokirajKupca(params){
    
    let username = params;
    
    //console.log(username);
    
    $.get({
        url: "rest/blokirajKupca?username=" + username,
        success: function () {
            alert('Korisnik je uspjesno blokiran!');
            window.location.href = "http://localhost:8080/PocetniREST/profil.html";
        }
    });

}

