function longDiv(s1, s2) {
    var c = compare(s1, s2);
    if(c < 0) {
        return "0";
    } else if(c == 0) {
        return "1";
    }

    var div = "";
    var end = false;

    end:
        while(true) {
            var s = s1.substr(0, s2.length);
            if(compare(s, s2) < 0) {
                s = s1.substr(0, s2.length+1);
            }

            for(var i = 0; i < 9; i++) {
                var mul = bigMul(s2, String(i));
                var cc = compare(mul, s);
                if(cc == 0) {
                    div = String(i) + div;
                    break end;
                } else if(cc > 0) {
                    mul = bigMul(s2, String(i-1));
                    var sub = longSubtract(s, mul);
                    s1 = sub + s1.substr(i+1);
                    div = String(i-1) + div;
                    break end;
                }
            }
        }

    return div;
}

function longSubtract(s1, s2) {
    var length = Math.max(s1.length, s2.length);
    if(s1.length != s2.length) {
        s1 = "0".repeat(length - s1.length) + s1;
        s2 = "0".repeat(length - s2.length) + s2;
    }

    var minus = false;

    if(compare(s1, s2) < 0) {
        var s = s1;
        s1 = s2;
        s2 = s;
        minus = true;
    }

    var sub = "";

    for(var i = length-1; i >= 0; i--) {
        var d1 = Number(s1.substr(i, 1));
        var d2 = Number(s2.substr(i, 1));

        if(d1 < d2) {
            for(var j = i-1; j >= 0; j--) {
                var d = Number(s1.substr(j, 1));
                if(d > 0) {
                    s1 = s1.replaceAt(j, String(d-1));
                    d1 += 10;
                    for(var k = j+1; k < i; k++) {
                        s1 = s1.replaceAt(k, "9");
                    }
                    break;
                }
            }
        }

        sub = String(d1-d2) + sub;
    }

    return sub;
}
