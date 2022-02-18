integer x := 100;

main
    integer i;
    % i "inserisci i: ";
    if i = 0 then
        ?: "i uguale a 0";
    elif i = 1 then
        var a := "ciao";
        ?: "i uguale a 1 " & x;
    else
        ?: "i diverso da 0 e 1";
    end if;

    if i = 0 then
    #* elif false then
    end if;

    if false then
    end if;

    if false then
    else
    end if;
end main;