$(document).ready(function(){    
    

    
    $('#forma').submit(function(event){
        event.preventDefault();
        let ime = $('input[name="ime"]').val();
        let prezime = $('input[name="prezime"]').val();
        let pol = $('select[name="pol"]').val();
        let datumRodjenja = $('input[name="datumRodjenja"]').val();
        let korisnickoIme = $('input[name="korisnickoIme"]').val();
        let lozinka = $('input[name="lozinka"]').val();
        let kupac = 3;
        let porudzbine = null;
        let korpa = null;
        let brBod = 0;
        let tipKupcas = null;
        if(!ime || !prezime || !datumRodjenja || !korisnickoIme || !lozinka){
            return alert("Unesite parametre.");
        }
        $('#error').hide();
        $.post({
            url: 'rest/registration',
            data: JSON.stringify({listaPorudzbina: porudzbine, korpa: korpa, brojSakupljenihBodova: brBod, korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: kupac, tipKupca: tipKupcas}),
            contentType: 'application/json',
            success: function(product){
                $('#success').text('Kupac je uspesno registrovan.');
                $('#success').show().delay(5000).fadeOut();
                window.location.href = "http://localhost:8080/PocetniREST/loginform.html";
            },
            error: function(message){
                $('#error').text(message.responseText);
                $('#error').show();
            }
        });
    });
});