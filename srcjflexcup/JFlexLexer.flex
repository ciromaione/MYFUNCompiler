import java_cup.runtime.*;

// C:\JFLEX\bin\jflex -d src srcjflexcup\JFlexLexer.flex
%%

%class Lexer
%cup
%unicode
%line
%column

%{
    private Symbol symbol(int token) {
          return new Symbol(token, yyline, yycolumn);
        }

        private Symbol symbol(int token, Object value) {
          return new Symbol(token, yyline, yycolumn, value);
        }

        StringBuffer string = new StringBuffer();
        int [] startPos = new int[]{ 0, 0};
%}

lineTerminator = \r|\n|\r\n
inputCharacter = [^\n\r]
ws = {lineTerminator} | [ \t\f]
letter = [a-zA-Z]
digit = [1-9]
digit_0 = [0{digit}]

// Keywords
MAIN = "main"
INTEGER = "integer"
STRING = "string"
REAL = "real"
BOOL = "bool"
VAR = "var"
FUN = "fun"
END = "end"
IF = "if"
THEN = "then"
ELSE = "else"
WHILE = "while"
LOOP = "loop"
RETURN = "return"
OUT = "out"
OUTPAR = "@"

// Identifiers
id = [{letter}_\$][{letter}_\${digit_0}]*

// Constants
REAL_CONST = (({digit}{digit_0}*)|0)\.{digit_0}*
INTEGER_CONST = (({digit}{digit_0}*)|0)
BOOL_CONST = "true" | "false"

// Operators
ASSIGN = ":="
PLUS = "+"
MINUS = "-"
TIMES = "*"
DIVINT = "div"
DIV = "/"
POW = "^"
STR_CONCAT = "&"
EQ = "="
NE = "<>"|"!="
LT = "<"
LE = "<="
GT = ">"
GE = ">="
AND = "and"
OR = "or"
NOT = "not"

// I/O
READ = "%"
WRITE = "?"
WRITELN = "?."
WRITEB = "?,"
WRITET = "?:"

// Separators
LPAR = "("
RPAR = ")"
COLON = ":"
SEMI = ";"
COMMA = ","

%state STRING_CONST_1
%state STRING_CONST_2
%state BLOCK_COMMENT
%state LINE_COMMENT

%%
<YYINITIAL> {
{ws} {/* ignore */}

// Keywords
{MAIN} { return symbol(sym.MAIN); }
{INTEGER} { return symbol(sym.INTEGER); }
{STRING} { return symbol(sym.STRING); }
{REAL} { return symbol(sym.REAL); }
{BOOL} { return symbol(sym.BOOL); }
{VAR} { return symbol(sym.VAR); }
{FUN} { return symbol(sym.FUN); }
{END} { return symbol(sym.END); }
{IF} { return symbol(sym.IF); }
{THEN} { return symbol(sym.THEN); }
{ELSE} { return symbol(sym.ELSE); }
"elif" { return symbol(sym.ELIF); }
{WHILE} { return symbol(sym.WHILE); }
{LOOP} { return symbol(sym.LOOP); }
{RETURN} { return symbol(sym.RETURN); }
{OUT} { return symbol(sym.OUT); }
{OUTPAR} { return symbol(sym.OUTPAR); }

// Operators
{ASSIGN} { return symbol(sym.ASSIGN); }
{PLUS} { return symbol(sym.PLUS); }
{MINUS} { return symbol(sym.MINUS); }
{TIMES} { return symbol(sym.TIMES); }
{DIVINT} { return symbol(sym.DIVINT); }
{DIV} { return symbol(sym.DIV); }
{POW} { return symbol(sym.POW); }
{STR_CONCAT} { return symbol(sym.STR_CONCAT); }
{EQ} { return symbol(sym.EQ); }
{NE} { return symbol(sym.NE); }
{LT} { return symbol(sym.LT); }
{LE} { return symbol(sym.LE); }
{GT} { return symbol(sym.GT); }
{GE} { return symbol(sym.GE); }
{AND} { return symbol(sym.AND); }
{OR} { return symbol(sym.OR); }
{NOT} { return symbol(sym.NOT); }

// I/O
{READ} { return symbol(sym.READ); }
{WRITE} { return symbol(sym.WRITE); }
{WRITELN} { return symbol(sym.WRITELN); }
{WRITEB} { return symbol(sym.WRITEB); }
{WRITET} { return symbol(sym.WRITET); }

// Separators
{LPAR} { return symbol(sym.LPAR); }
{RPAR} { return symbol(sym.RPAR); }
{COLON} { return symbol(sym.COLON); }
{SEMI} { return symbol(sym.SEMI); }
{COMMA} { return symbol(sym.COMMA); }

// Constants
{REAL_CONST} { return symbol(sym.REAL_CONST, Float.parseFloat(yytext())); }
{INTEGER_CONST} { return symbol(sym.INTEGER_CONST, Integer.parseInt(yytext())); }
{BOOL_CONST} { return symbol(sym.BOOL_CONST, Boolean.parseBoolean(yytext())); }

// Identifiers
{id} { return symbol(sym.ID, yytext()); }

\" { string = new StringBuffer(); startPos[0] = yyline; startPos[1] = yycolumn; yybegin(STRING_CONST_1); }
\' { string = new StringBuffer(); startPos[0] = yyline; startPos[1] = yycolumn; yybegin(STRING_CONST_2); }

"#*" { startPos[0] = yyline; startPos[1] = yycolumn; yybegin(BLOCK_COMMENT); }
# { yybegin(LINE_COMMENT); }

// Error
[^] { throw new Error("Illegal character <" + yytext() + "> at " + yyline + ", " + yycolumn); }
}

<STRING_CONST_1> {
\" { yybegin(YYINITIAL); return new Symbol(sym.STRING_CONST, string.toString()); }
[^\n\r\"\\]+ { string.append( yytext() ); }
\\t { string.append("\\t"); }
\\n { string.append("\\n"); }
\\r { string.append("\\r"); }
\\\" { string.append("\\\""); }
\\ { string.append('\\'); }
<<EOF>> { throw new Error("String not closed at " + startPos[0] + ", " + startPos[1]); }
}

<STRING_CONST_2> {
\' { yybegin(YYINITIAL); return new Symbol(sym.STRING_CONST, string.toString()); }
[^\n\r\'\\]+ { string.append( yytext() ); }
\\t { string.append("\\t"); }
\\n { string.append("\\n"); }
\\r { string.append("\\r"); }
\\\" { string.append("\\\""); }
\\ { string.append('\\'); }
<<EOF>> { throw new Error("String not closed at " + startPos[0] + ", " + startPos[1]); }
}

<BLOCK_COMMENT> {
"#" { yybegin(YYINITIAL); }
<<EOF>> { throw new Error("Comment not close " + startPos[0] + ", " + startPos[1]); }
[^] { /* Ignore */ }
}

<LINE_COMMENT> {
{lineTerminator} { yybegin(YYINITIAL); }
[^] { /* Ignore */ }
}