
$(document).ready(function(){
    $('#forma').submit(function(event){
        event.preventDefault();
        getStariKupac(doSomeThingWithOldKupac);
    });
});
function getStariKupac(callbackFunc){
    $.ajax({
		url : "rest/getSession"
	}).then(function(korisnik) {
        callbackFunc(korisnik)
	});
}
function doSomeThingWithOldKupac(korisnik){
    if(korisnik.uloga === "Kupac"){
        let ime = $('input[name="ime"]').val();
        let prezime = $('input[name="prezime"]').val();
        let pol = $('select[name="pol"]').val();
        let datumRodjenja = $('input[name="datumRodjenja"]').val();
        let korisnickoIme = $('input[name="korisnickoIme"]').val();
        let lozinka = $('input[name="lozinka"]').val();
        let kupacc = korisnik.uloga;
        let porudzbine = korisnik.porudzbine;
        let korpa = korisnik.korpa;
        let brBod = korisnik.brojSakupljenihBodova;
        let tipKupcas = korisnik.tipKupca;
        if(!ime || !prezime || !datumRodjenja || !korisnickoIme || !lozinka){
            return alert("Unesite parametre.");
        }
        $('#error').hide();
        $.post({
            url: 'rest/editKupac',
            data: JSON.stringify({listaPorudzbina: porudzbine, korpa: korpa, brojSakupljenihBodova: brBod, korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: kupacc, tipKupca: tipKupcas}),
            contentType: 'application/json',
            success: function(product){
                $('#success').text('Kupac je uspesno izmenjen.');
                $('#success').show().delay(5000).fadeOut();
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });
    }
    else if(korisnik.uloga === "Administrator"){
        let ime = $('input[name="ime"]').val();
        let prezime = $('input[name="prezime"]').val();
        let pol = $('select[name="pol"]').val();
        let datumRodjenja = $('input[name="datumRodjenja"]').val();
        let korisnickoIme = $('input[name="korisnickoIme"]').val();
        let lozinka = $('input[name="lozinka"]').val();
        let uloga = korisnik.uloga;
        let tipKupcas = korisnik.tipKupca;
        if(!ime || !prezime || !datumRodjenja || !korisnickoIme || !lozinka){
            return alert("Unesite parametre.");
        }
        $('#error').hide();
        $.post({
            url: 'rest/editAdministrator',
            data: JSON.stringify({korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: uloga, tipKupca: tipKupcas}),
            contentType: 'application/json',
            success: function(product){
                $('#success').text('Administrator je uspesno izmenjen.');
                $('#success').show().delay(5000).fadeOut();
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });
    }
    else if(korisnik.uloga === "Menadzer"){
        let ime = $('input[name="ime"]').val();
        let prezime = $('input[name="prezime"]').val();
        let pol = $('select[name="pol"]').val();
        let datumRodjenja = $('input[name="datumRodjenja"]').val();
        let korisnickoIme = $('input[name="korisnickoIme"]').val();
        let lozinka = $('input[name="lozinka"]').val();
        let uloga = korisnik.uloga;
        let tipKupcas = korisnik.tipKupca;
        if(!ime || !prezime || !datumRodjenja || !korisnickoIme || !lozinka){
            return alert("Unesite parametre.");
        }
        $('#error').hide();
        $.post({
            url: 'rest/editMenadzer',
            data: JSON.stringify({korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: uloga, tipKupca: tipKupcas}),
            contentType: 'application/json',
            success: function(product){
                $('#success').text('Menadzer je uspesno izmenjen.');
                $('#success').show().delay(5000).fadeOut();
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });
    }
    else if(korisnik.uloga === "Dostavljac"){
        let ime = $('input[name="ime"]').val();
        let prezime = $('input[name="prezime"]').val();
        let pol = $('select[name="pol"]').val();
        let datumRodjenja = $('input[name="datumRodjenja"]').val();
        let korisnickoIme = $('input[name="korisnickoIme"]').val();
        let lozinka = $('input[name="lozinka"]').val();
        let uloga = korisnik.uloga;
        let tipKupcas = korisnik.tipKupca;
        if(!ime || !prezime || !datumRodjenja || !korisnickoIme || !lozinka){
            return alert("Unesite parametre.");
        }
        $('#error').hide();
        $.post({
            url: 'rest/editDostavljac',
            data: JSON.stringify({korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: uloga, tipKupca: tipKupcas}),
            contentType: 'application/json',
            success: function(product){
                $('#success').text('Dostavljac je uspesno izmenjen.');
                $('#success').show().delay(5000).fadeOut();
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });
    }
    
}

