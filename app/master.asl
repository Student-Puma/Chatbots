// Agent master in project proyecto.mas2j

/* Initial beliefs and rules */

respuesta(1).

service(Answer, mailing) :-
	checkTag("<mail>",Answer).
checkTag(String,Answer) :-
	.substring("<mail>",Answer).

service(Answer, addset) :-
	checkTag("<addset>",Answer).
checkTag(String,Answer) :-
	.substring("<addset>",Answer).





getValTag(Tag,String,Val) :- 
	.substring(Tag,String,Fst) &
	.length(Tag,N) &
	.delete(0,Tag,RestTag) &
	.concat("</",RestTag,EndTag) &
	.substring(EndTag,String,End) &
	.substring(String,Val,Fst+N,End).



filter(Respuesta, mailing, Proxy) :-
	getValTag("<to>",Respuesta,To) &
	getValTag("<subject>",Respuesta,Subject) &
	getValTag("<msg>",Respuesta,Msg) &
	.concat("Acabo de enviar el mensaje a ", Subject, Proxy) &
	gui.mailing("spam.kikefontanlorenzo@gmail.com","TOT","O").


filter(Respuesta, addset, Proxy) :-
	getValTag("<to>",Respuesta,To) &
	getValTag("<new>",Respuesta,New) &
	gui.addValueOnSetFile(To,New,"proyecto") &
	Proxy = "Añadido al archivo".

/* Initial goals */

!start.

/* Plans */

+!start : bot(created) <-
	+nothing.

+answer(Respuesta) : respuesta(N) <-
	// -answer(Respuesta)[source(Source)];
	-+respuesta(N + 1);
	+contestacion(N, Respuesta);
	/*
	if ( service(Respuesta, mailing) ) {
		.println;
		.println(" >> Procesando peticion de mailing << ");
		filter(Respuesta, mailing, Proxy);
	}
	*/
	if ( service(Respuesta, addset) ) {
		.println;
		.println(" >> Procesando peticion para anadir << ");
		filter(Respuesta, addset, Proxy);
	}
	.send(student, tell, answer(Proxy));
	.println(Proxy);
	.wait(1000).


