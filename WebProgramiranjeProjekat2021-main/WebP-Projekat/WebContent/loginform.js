$(document).ready(function(){

    $('#login-form').submit(logovanje());
});


function logovanje() {
	return function(event) {
		event.preventDefault();
		let username = $('input[name="korisnickoIme"]').val();
		let password = $('input[name="lozinka"]').val();
		$.ajax({
			type : 'POST',
			url : 'rest/login',
			data : JSON.stringify({
				korisnickoIme : username,
				lozinka : password
			}),
			contentType : 'application/json',
			success : function() {
				window.location.href = "http://localhost:8080/PocetniREST/index.html";
			},
			error : function(message) {
				alert(message.responseText);
			}
		});
	}
}