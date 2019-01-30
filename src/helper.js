String.prototype.replaceAt = function(index, replace) {
    return this.substr(0, index) + replace + this.substr(index + replace.length);
}

String.prototype.count = function(ch) {
    var c = 0;
    for(var i = 0; i < this.length-ch.length+1; i++) {
        if(this.substr(i, ch.length) === ch) {
            c++;
        }
    }

    return c;
}


function longSum(s1, s2) {
    var sg1 = (s1.startsWith("-") ? -1 : 1);
    if(sg1 < 0) {
        s1 = s1.substring(1);
    }
    var sg2 = (s2.startsWith("-") ? -1 : 1);
    if(sg2 < 0) {
        s2 = s2.substring(1);
    }

    if((sg1 < 0) && (sg2 > 0)) {
        return longSubtract(s2, s1);
    } else if((sg1 > 0) && (sg2 < 0)) {
        return longSubtract(s1, s2);
    }

    if(s1.length !== s2.length) {
        var length = Math.max(s1.length, s2.length);
        s1 = "0".repeat(length - s1.length) + s1;
        s2 = "0".repeat(length - s2.length) + s2;
    }

    var sum = "";
    var borrow = 0;

    for(var i = s1.length - 1; i >= 0; i--) {
        var n1 = Number(s1.substr(i, 1));
        var n2 = Number(s2.substr(i, 1));
        var n = borrow + n1 + n2;
        borrow = Math.floor(n / 10);
        sum = (n % 10) + sum;
    }

    if(borrow > 0) {
        sum = borrow + sum;
    }

    return (sg1*sg2 < 0 ? "-" : "") + trim0(sum);
}

function longSubtract(s1, s2) {
    var sg1 = (s1.startsWith("-") ? -1 : 1);
    if(sg1 < 0) {
        s1 = s1.substring(1);
    }
    var sg2 = (s2.startsWith("-") ? -1 : 1);
    if(sg2 < 0) {
        s2 = s2.substring(1);
    }

    if(sg1 !== sg2) {
        return (sg1 < 0 ? "-" : "") + longSum(s1, s2);
    }

    var length = Math.max(s1.length, s2.length);
    if(s1.length !== s2.length) {
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

    return (minus ? "-" : "") + trim0(sub);
}

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
                var mul = longMul(s2, String(i));
                var cc = compare(mul, s);
                if(cc === 0) {
                    div = String(i) + div;
                    break end;
                } else if(cc > 0) {
                    mul = longMul(s2, String(i-1));
                    var sub = longSubtract(s, mul);
                    s1 = sub + s1.substr(i+1);
                    div = String(i-1) + div;
                    break end;
                }
            }
        }

    return div;
}


function arraySum(arr) {
    var sum = arr[0];

    for(var i = 1; i < arr.length; i++) {
        sum = longSum(sum, arr[i]);
    }

    return sum;
}

function trim0(s) {
    var i;
    for(i = 0; s.substr(i, 1) === "0"; i++);
    return s.substring(i);
}


function longMul(s1, s2) {
    var arr = [];
    var lastIndex = s1.length - 1;

    for(var i = lastIndex; i >= 0; i--) {
        var mul = singleMul(s2, s1.substr(i, 1)) + "0".repeat(lastIndex - i);
        arr.push(mul);
    }

    return arraySum(arr);
}


function singleMul(long, single) {
    mul = "";
    var borrow = 0;

    for(var i = long.length - 1; i >= 0; i--) {
        var n1 = Number(long.substr(i, 1));
        var n2 = Number(single);
        var n = borrow + n1 * n2;
        borrow = Math.floor(n / 10);
        mul = (n % 10) + mul;
    }

    if(borrow > 0) {
        mul = borrow + mul;
    }

    return mul;
}

function arrayEquals(arr1, arr2) {
    if(arr1 === arr2) {
        return true;
    }

    if(arr1.length !== arr2.length) {
        return false;
    }

    var equal = true;
    for(var i = 0; i < arr1.length; i++) {
        equal &= (arr1[i] === arr2[i]);
    }

    return equal;
}

function factorial(n) {
    var f = "1";

    for(var i = 1; i <= n; i++) {
        f = longMul(f, String(i));
    }

    // while(f.startsWith("0")) {
    //     f = f.substr(1);
    // }

    return trim0(f);
}

function getProperDivisorsSum(n) {
    var sum = 0;

    for(var i = 1; i < n; i++) {
        if(n % i === 0) {
            sum += i;
        }
    }

    return sum;
}

function power(a, b) {
    if(a === 0) {
        if(b === 0) {
            return undefined;
        } else {
            return "0";
        }
    } else if(b === 0) {
        return "1";
    }

    var result = "1";

    for(var i = 0; i < b; i++) {
        result = longMul(result, String(a));
    }

    return result;
}


function preparePrimes(n) {
    var primes = [2];
    var i = 3;

    while(i < n) {
        var sqrt = Math.floor(Math.sqrt(i));
        var prime = true;

        for(var j = 0; primes[j] <= sqrt; j++) {
            prime = prime && (i % primes[j] != 0);
        }

        if(prime) {
            primes.push(i);
        }

        i += 2;
    }

    return primes;
}

function isPrimeGeneric(n) {
    var sqrt = Math.floor(Math.sqrt(n));
    var primes = preparePrimes(sqrt);
    return isPrime(primes, n);
}

function isPrime(primes, n) {
    if(n < 2) {
        return false;
    }

    var sqrt = Math.floor(Math.sqrt(n));
    var prime = true;

    for(var j = 0; primes[j] <= sqrt; j++) {
        prime = prime && (n % primes[j] !== 0);
    }

    return prime;
}

function isPandigital(n, start, end) {
    var s = String(n);
    if(s.length !== end-start+1) {
        return false;
    }

    var pd = true;
    for(var i = start; i < end + 1; i++) {
        pd = pd && s.includes(String(i));
    }

    return pd;
}

function getPrimeFactors(primes, n) {
    var set = {};

    var ubound = Math.floor(Math.sqrt(n));
    for(var i = 0; primes[i] <= ubound; i++) {
        var prime = primes[i];
        if(n % prime === 0) {
            var count = set[prime];
            if(!count) {
                count = 0;
            }
            set[prime] = ++count;
            n /= prime;
            i--;
        }
    }

    if(n > 1) {
        var count = set[n];
        if(!count) {
            count = 0;
        }
        set[n] = ++count;
    }

    return set;
}

function padLeft(arr, ch) {
    var length = 0;

    for(var i = 0; i < arr.length; i++) {
        if(length < arr[i].length) {
            length = arr[i].length;
        }
    }

    for(var i = 0; i < arr.length; i++) {
        arr[i] = ch.repeat(length - arr[i].length) + arr[i];
    }
}


function getObjectSize(set) {
    var s = 0;
    for(var p in set) {
        if(set.hasOwnProperty(p)) {
            s++;
        }
    }

    return s;
}

function isPalindrome(s) {
    return s === reverse(s);
}

function reverse(s) {
    return s.split("").reverse().join("");
}

function digitalSum(s) {
    let ss = s.replace(".", "");
    var sum = 0;
    for(var i = 0; i < ss.length; i++) {
        sum += Number(ss.substr(i, 1));
    }
    return sum;
}

function compare(s1, s2) {
    var length = Math.max(s1.length, s2.length);
    if(s1.length !== s2.length) {
        s1 = "0".repeat(length - s1.length) + s1;
        s2 = "0".repeat(length - s2.length) + s2;
    }

    if(s1 > s2) {
        return 1;
    } else if(s1 < s2) {
        return -1;
    }

    return 0;
}

function phi(primes, n) {
    var set = getPrimeFactors(primes, n);
    var coprimes = n;
    for(var key in set) {
        coprimes *= (1-1/key);
    }

    return coprimes;
}

function sqrtExperimental(n, f) {
    let result = "";
    let s0 = String(n);
    if(s0.length % 2 === 1) {
        s0 = "0" + s0;
    }
    s = s0 + "0".repeat(f*2);

    let p = "";
    for(let i = 0; i < s.length; i += 2) {
        if(i === s0.length) {
            result += ".";
        }
        p = p + s.substr(i, 2);

        if(i === 0) {
            let root = Math.floor(Math.sqrt(Number(p)));
            result += String(root);
            let sqr = root*root;
            // p = longSubtract(p, String(sqr));
            p = String(p-sqr);
        } else {
            // let t = longMul(result.replace(".", ""), "20");
            let t = Number(result.replace(".", ""))*20;
            let found = false;
            for(let d = 0; d <= 10; d++) {
                // let term = longMul(longSum(t, String(d)), String(d));
                let term = (t+d)*d;
                // if(compare(term, String(p)) > 0) {
                if(term > p) {
                    d--;
                    // term = longMul(longSum(t, String(d)), String(d));
                    term = (t+d)*d;
                    result += String(d);
                    // p = longSubtract(p, term);
                    p = String(p-term);
                    found = true;
                    break;
                }
            }
            if(!found) {
                result += "0";
            }
        }
    }

    return result;
}

function sqrtGeneric(n, f) {
    let result = "";
    let s0 = String(n);
    if(s0.length % 2 === 1) {
        s0 = "0" + s0;
    }
    s = s0 + "0".repeat(f*2);

    let p = "";
    for(let i = 0; i < s.length; i += 2) {
        if(i === s0.length) {
            result += ".";
        }
        p = p + s.substr(i, 2);

        if(i === 0) {
            let root = Math.floor(Math.sqrt(Number(p)));
            result += String(root);
            let sqr = root*root;
            p = longSubtract(p, String(sqr));
        } else {
            let t = longMul(result.replace(".", ""), "20");
            let found = false;
            for(let d = 0; d <= 10; d++) {
                let term = longMul(longSum(t, String(d)), String(d));
                if(compare(term, String(p)) > 0) {
                    d--;
                    term = longMul(longSum(t, String(d)), String(d));
                    result += String(d);
                    p = longSubtract(p, term);
                    found = true;
                    break;
                }
            }
            if(!found) {
                result += "0";
            }
        }

        // if(p === "") {
        //     break;
        // }
    }

    return result;
}



function log(text, value) {
    document.write(text + ": " + value + "<br>");
}

