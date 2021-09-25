$(document).ready(function(){
    $('#formaMenadzera').submit(function(event){
        event.preventDefault();
        let restoran = null;
        let ime = $('input[name="imem"]').val();
        let prezime = $('input[name="prezimem"]').val();
        let pol = $('select[name="polm"]').val();
        let datumRodjenja = $('input[name="datumRodjenjam"]').val();
        let korisnickoIme = $('input[name="korisnickoImem"]').val();
        let lozinka = $('input[name="lozinkam"]').val();
        let uloga = 1;
        let tipKupcas = null;

        if(!ime || !prezime || !datumRodjenja || !korisnickoIme || !lozinka){
            return alert("Unesite parametre.");
        }
        $.post({
            url: 'rest/createMenadzer',
            data: JSON.stringify({restoran: restoran, korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: uloga, tipKupca: tipKupcas}),
            contentType: 'application/json',
            success: function(message){
                $('#success').text('Menadzer je uspesno registrovan.');
                $('#success').show().delay(5000).fadeOut();
                window.location.href = "http://localhost:8080/PocetniREST/profil.html";
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });
    });

    $('#formaDostavljaca').submit(function(event){
        event.preventDefault();
        let listaPorudzbinaZaDostaviti = null;
        let ime = $('input[name="imed"]').val();
        let prezime = $('input[name="prezimed"]').val();
        let pol = $('select[name="pold"]').val();
        let datumRodjenja = $('input[name="datumRodjenjad"]').val();
        let korisnickoIme = $('input[name="korisnickoImed"]').val();
        let lozinka = $('input[name="lozinkad"]').val();
        let uloga = 2;
        let tipKupcas = null;

        if(!ime || !prezime || !datumRodjenja || !korisnickoIme || !lozinka){
            return alert("Unesite parametre.");
        }

        $.post({
            url: 'rest/createDostavljac',
            data: JSON.stringify({	listaPorudzbinaZaDostaviti: listaPorudzbinaZaDostaviti, korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: uloga, tipKupca: tipKupcas}),
            contentType: 'application/json',
            success: function(message){
                $('#success').text('Dostavljac je uspesno registrovan.');
                $('#success').show().delay(5000).fadeOut();
                window.location.href = "http://localhost:8080/PocetniREST/profil.html";
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });
    });
});