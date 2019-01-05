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

