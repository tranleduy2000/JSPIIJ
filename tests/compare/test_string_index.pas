var
    s: string;
    i: integer;
begin
    s := 'abcdef';
    for i := 1 to length(s) do write(s[i]);
    writeln;
    for i := 1 to length(s) do s[i] := s[Length(s) - i + 1];
    write(s);
end.