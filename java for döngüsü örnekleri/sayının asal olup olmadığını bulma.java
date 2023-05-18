int number = 19;
boolean isPrime = true;

for (int i = 2; i <= Math.sqrt(number); i++) {
    if (number % i == 0) {
        isPrime = false;
        break;
    }
}

if (isPrime) {
    System.out.println(number + " bir asal sayıdır.");
} else {
    System.out.println(number + " bir asal sayı değildir.");
}
