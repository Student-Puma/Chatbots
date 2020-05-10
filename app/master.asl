// Agente master
// Autor: Diego Enrique Fontán Lorenzo

/**************** Conocimiento ****************/

// Empezamos el proceso de pregunta - respuesta

respuesta(1).

// -- Etiquetas -- //

filter(Respuesta, mailing, To) :-
	getValTag("<to>",Respuesta,To) &
	getValTag("<subject>",Respuesta,Subject) &
	getValTag("<msg>",Respuesta,Msg) &
	gui.mailing(To,Subject,Msg).
	
filter(Respuesta, addset, To) :- 
	getValTag("<new>",Respuesta,New) &
	getValTag("<out>",Respuesta,To) &
	.lower_case(To,File) &
	gui.addValueOnSetFileFor(New,File,"proyecto").
	
filter(Respuesta, addmap, To) :- 
	getValTag("<new>",Respuesta,Par) &
	getValTag("<out>",Respuesta,To) &
	split(Par,":",K,V) &
	.lower_case(To,File) &
	gui.addRelOnMapFileFor(K,V,File,"proyecto").

// -- Etiquetas -- //
	
split(String, Delim, Izq, Drch) :-
	.length(String,L) & .length(Delim, D) &
	.substring(Delim, String, Pos) &
	.substring(String,Izq,0,Pos) &
	.substring(String,Drch,Pos + D,L).

getValTag(Tag,String,Val) :- 
	.substring(Tag,String,Fst) &
	.length(Tag,N) &
	.delete(0,Tag,RestTag) &
	.concat("</",RestTag,EndTag) &
	.substring(EndTag,String,End) &
	.substring(String,Val,Fst+N,End).

// -- Filtro de conversaciones -- //

valida(Respuesta) :-
	.substring("<mail>", Respuesta) | .substring("<new>", Respuesta).

/****************** Metas ******************/

!start.

/***************** Planes ******************/

// -- Entrypoint -- //

+!start : bot(created).

// -- Obtener respuestas -- //

+answer(Respuesta) : valida(Respuesta) <-
	-answer(Respuesta)[source(Source)];
	?respuesta(N);
	-+respuesta(N + 1);
	+contestacion(N, Respuesta);
	.println("Analizando el mensaje");
	
	if ( .substring("<addset>", Respuesta) ) {
		.println("> Procesando peticion de addset");
		?filter(Respuesta, addset, To);
		.send(student, tell, answer("Incluido en la lista correctamente"));
	};
	if ( .substring("<addmap>", Respuesta) ) {
		.println("> Procesando peticion de addmap");
		?filter(Respuesta, addmap, To);
		.send(student, tell, answer("Incluido en la seleccion correctamente"));
	};
	if ( .substring("<mail>", Respuesta) ) {
		.println("> Procesando peticion de mailing");
		?filter(Respuesta, mailing, To);
		.send(student, tell, answer("Acabo de mandar la peticion de la que hablamos"));
	};
	.wait(1000).
