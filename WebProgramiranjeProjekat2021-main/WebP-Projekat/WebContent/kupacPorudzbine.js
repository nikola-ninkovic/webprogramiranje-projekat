var svePorudzbine = [];
var all;
$(document).ready(function(){
        $('#komentar').hide();
        $.ajax({
            url : "rest/getPorudzbine"
        }).then(function(listaPorudzbina) {
                all = listaPorudzbina;
                for(var x=0; x<listaPorudzbina.length; x++){
                        $(".rowww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '<br>');
                        svePorudzbine.push(listaPorudzbina[x]);
                        if( listaPorudzbina[x].statusPorudzbine === "Obrada" ){
                             $(".col-5:last").append('<button style="width:200px" onclick="otkaziPorudzbinu(' + listaPorudzbina[x].id + "," + listaPorudzbina[x].cena + ')">otkazi</button>');
                        }
                        if( listaPorudzbina[x].statusPorudzbine === "Dostavljena" ){
                                $(".col-5:last").append('<button id="'+ x +'" style="width:200px" onclick="komentarisi(' + listaPorudzbina[x].id + ')">Komentarisi</button>');
                        }
                        if( listaPorudzbina[x].koment === "da" ){
                                $('#'+ x).prop('disabled', true);
                        }
                        $(".rowww:last").append('</p> </div>');
                    
                    
                }
           
        })

        $('#nedostavljene').click(function(){
                $('.rowww').empty();
                $('#komentar').hide();
                izlistajMojeNedostavljenePorudzbine();
        })
        $('#mojePorudzbine').click(function(){
                $('.rowww').empty();
                $('#komentar').hide();
                izlistajMojePorudzbine(all);
                for(var x=0; x<all.length; x++){
                        svePorudzbine.push(all[x]);
                }
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
                $('#komentar').hide();
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
    
function komentarisi(id){
        $('.col-5').hide();
        $('#komentar').show();
        $('#potvrdi').click(function(){
                event.preventDefault();
                let tekst = $('textarea#textarea').val();
                let ocena = $('#ocena').val();
                if(!tekst || !ocena ) return alert("Unesite sve parametre");
                $.get({
                        url: "rest/getPorudzbinu?id=" + id,
                        success: function (narudzbina) {
                                narudzbina;
                                $.post({
                                        url: "rest/kreirajKomentar",
                                        data: JSON.stringify({ porudzbina: narudzbina, restoran: narudzbina.restoran, tekst: tekst, ocena: ocena }),
                                        contentType: "application/json",
                                        success: function () {
                                                window.location.href = "http://localhost:8080/PocetniREST/kupacPorudzbine.html";
                                        }
                                });
                        }
                });
        })
        
        
}
    
function otkaziPorudzbinu(id, cena) {
    
        $.post({
        url: "rest/otkaziPorudzbinu",
        data: JSON.stringify({ id: id, cena: cena }),
        contentType: "application/json",
        success: function () {
            alert('Porudzbina je uspjesno otkazana!');
            window.location.href = "http://localhost:8080/PocetniREST/kupacPorudzbine.html";
            
        }
    });

}
function izlistajMojeNedostavljenePorudzbine(){
        $.ajax({
            url : "rest/loginStatusKupac"
        }).then(function(user) {
                svePorudzbine = [];
                let listaPorudzbina = user.listaPorudzbina;
                for(var x=0; x<listaPorudzbina.length; x++){
                        if( listaPorudzbina[x].statusPorudzbine != "Dostavljena" ){
                        $(".rowww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '</p> </div>');
                        svePorudzbine.push(listaPorudzbina[x]);
                        }
                }
    
        });
}
function izlistajMojePorudzbine(listaPorudzbina){
        $(".rowww").empty();
        for(var x=0; x<listaPorudzbina.length; x++){
                $(".rowww:last").append('<div class="col-5">' + '<h3>' + listaPorudzbina[x].id + '</h3> <p>Ukupna cena: ' + listaPorudzbina[x].cena + ' rsd <br>Status: ' + listaPorudzbina[x].statusPorudzbine + ' <br>Restoran: ' + listaPorudzbina[x].restoran.naziv + '<br>Datum:' + listaPorudzbina[x].datumPorudzbine + '<br>');
                
                if( listaPorudzbina[x].statusPorudzbine === "Obrada" ){
                     $(".col-5:last").append('<button style="width:200px" onclick="otkaziPorudzbinu(' + listaPorudzbina[x].id + "," + listaPorudzbina[x].cena + ')">otkazi</button>');
                }
                if( listaPorudzbina[x].statusPorudzbine === "Dostavljena" ){
                        $(".col-5:last").append('<button id="'+ x +'" style="width:200px" onclick="komentarisi(' + listaPorudzbina[x].id + ')">Komentarisi</button>');
                }
                if( listaPorudzbina[x].koment === "da" ){
                        $('#'+ x).prop('disabled', true);
                }
                
                $(".rowww:last").append('</p> </div>');
        }
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

	izlistajMojePorudzbine(SVEPorudzbine);
}