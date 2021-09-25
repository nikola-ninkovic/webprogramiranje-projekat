$(document).ready(function(){
    $.ajax({
		url : "rest/loginStatusMenadzer"
	}).then(function(user) {
		if (user != undefined) {
			$(".restoran").empty();
            $.ajax({
                url : "rest/getRestoran?id=" + user.restoran.naziv +""
            }).then(function(lista) {
                $(".restoran:last").append('<img src="' + lista.logoRestorana +'">');
                $(".restoran:last").append('<h1>' + lista.naziv +'</h1>');
                $(".restoran:last").append('<h3>' + lista.tipRestorana +'</h3>');
                $(".restoran:last").append('<h3>' + lista.status +'</h3>');
                $(".restoran:last").append('<h4>' + lista.lokacija.ulica +'</h4>');
                $(".restoran:last").append('<h4>' + lista.lokacija.grad +'</h4>');
                $(".restoran:last").append('<h4> Ocena:'+ lista.prosecnaOcena +'</h4>');

                $.ajax({
                    url: "rest/getArtikle"
                }).then(function(artikli){
                    $(".roww:last").empty();
                    for(var x=0; x<artikli.length; x++){
                        if(lista.naziv === artikli[x].imeRestorana){
                            $(".roww:last").append('<div class="col-5">' + '<img src="' + artikli[x].slika + '" >' + '<h3>' + artikli[x].naziv + '</h3> <p>Cena: ' + artikli[x].cena + ' rsd <br>Kolicina: ' + artikli[x].kolicina + '<br>Opis: ' + artikli[x].opis +'</p> </div>');
                        }
                    }
                })

                $.ajax({
                    url: "rest/getKomentare"
                }).then(function(komentari){
                    $(".rowwww:last").empty();
                    for(var x=0; x<komentari.length; x++){
                        if(lista.naziv === komentari[x].restoran.naziv){
                            $(".rowwww:last").append('<div class="col-5">' + '<h2>' + komentari[x].tekst + '</h2> <p>Ocena: ' + komentari[x].ocena + '</p> </div>');
                            $(".col-5:last").append('<p> Zahtev: '+ komentari[x].porudzbina.koment +'</p>');
                            
                        }
                        if(komentari[x].porudzbina.koment != "potvdjen" && komentari[x].porudzbina.koment != "odbijen"){
                            $(".col-5:last").append('<button onclick="potvrdi(' + komentari[x].id + ')">Potvrdi</button>');
                            $(".col-5:last").append('<button onclick="otkazi(' + komentari[x].id + ')">Odbij</button>');
                        }
                    }
                })
            })
		}
	});
});

function potvrdi(id){
    $.post({
        url: "rest/potvrdiKomentar",
        data: JSON.stringify({ id: id}),
        contentType: "application/json",
        success: function () {
            window.location.href = "http://localhost:8080/PocetniREST/mojRestoran.html";
        }
        });
}
function otkazi(id){
    $.post({
        url: "rest/odbijKomentar",
        data: JSON.stringify({ id: id}),
        contentType: "application/json",
        success: function () {
            window.location.href = "http://localhost:8080/PocetniREST/mojRestoran.html";
        }
        });
}