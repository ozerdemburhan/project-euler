String.prototype.replaceAt = function(index, replace) {
    return this.substr(0, index) + replace + this.substr(index + replace.length);
}

String.prototype.count = function(ch) {
    var c = 0;
    for(var i = 0; i < this.length-ch.length+1; i++) {
        if(this.substr(i, ch.length) == ch) {
            c++;
        }
    }

    return c;
}


function bigSum(s1, s2) {
    if(s1.length != s2.length) {
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

    return sum;
}


function arraySum(arr) {
    var sum = arr[0];

    for(var i = 1; i < arr.length; i++) {
        sum = bigSum(sum, arr[i]);
    }

    return sum;
}


function bigMul(s1, s2) {
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
    if(arr1 == arr2) {
        return true;
    }

    if(arr1.length != arr2.length) {
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
        f = bigMul(f, String(i));
    }

    return f;
}

function getProperDivisorsSum(n) {
    var sum = 0;

    for(var i = 1; i < n; i++) {
        if(n % i == 0) {
            sum += i;
        }
    }

    return sum;
}

function power(a, b) {
    if(a == 0) {
        if(b == 0) {
            return undefined;
        } else {
            return "0";
        }
    } else if(b == 0) {
        return "1";
    }

    var result = "1";

    for(var i = 0; i < b; i++) {
        result = bigMul(result, String(a));
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
        prime = prime && (n % primes[j] != 0);
    }

    return prime;
}

function isPandigital(n, start, end) {
    var s = String(n);
    if(s.length != end-start+1) {
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
        if(n % prime == 0) {
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
    return s == reverse(s);
}

function reverse(s) {
    return s.split("").reverse().join("");
}

function digitalSum(s) {
    var sum = 0;
    for(var i = 0; i < s.length; i++) {
        sum += Number(s.substr(i, 1));
    }
    return sum;
}

function compare(s1, s2) {
    var length = Math.max(s1.length, s2.length);
    if(s1.length != s2.length) {
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



function log(text, value) {
    document.write(text + ": " + value + "<br>");
}

