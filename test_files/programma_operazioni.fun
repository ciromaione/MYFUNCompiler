
integer scelta := -1;

fun print_menu()
  ?. "Scegli l'operazione da svolgere per continuare";
  ?. '(1) Addizione';
  ?. '(2) Sottrazione';
  ?. '(3) Moltiplicazione';
  ?. '(4) Divisione';
  ?. '(0) Esci';
  % scelta '\t > ';
end fun;

fun stampa_continuare()
  string continuare;
  ?. 'Vuoi continuare? (s/n)';
  % continuare '\t > ';
  if continuare != 's' then
    scelta := 0;
  end if;
end fun;

fun leggi_operandi(out real op1, out real op2)
  % op1 'Inserisci il primo operando\t > ';
  % op2 'Inserisci il secondo operando\t > ';
end fun;

fun somma_op()
  real op1, op2, result;
  leggi_operandi(@op1, @op2);
  result := op1 + op2;
  ?. "Il risultato dell'addizione è " & result;
  stampa_continuare();
end fun;

fun sottrazione_op()
  real op1, op2, result;
  leggi_operandi(@op1, @op2);
  result := op1 - op2;
  ?. 'Il risultato della sottrazione è ' & result;
  stampa_continuare();
end fun;

fun moltiplicazione_op()
  real op1, op2, result;
  leggi_operandi(@op1, @op2);
  result := op1 * op2;
  ?. 'Il risultato della moltiplicazione è ' & result;
  stampa_continuare();
end fun;

fun divisione_op()
  real op1, op2, result;
  leggi_operandi(@op1, @op2);
  result := op1 / op2;
  ?. 'Il risultato della divisione è ' & result;
  stampa_continuare();
end fun;

main
  while scelta != 0 loop
    print_menu();

    if scelta = 1 then
      somma_op();
    else
      if scelta = 2 then
        sottrazione_op();
      else
        if scelta = 3 then
          moltiplicazione_op();
        else
          if scelta = 4 then
            divisione_op();
          else
            ?. 'Valore inserito non valido, riprova!';
          end if;
        end if;
      end if;
    end if;

  end loop;
end main;