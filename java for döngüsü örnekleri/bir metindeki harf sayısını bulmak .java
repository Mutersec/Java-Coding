String text = "Ronesa , Yapımcı Muter!";
int count = 0;

for (int i = 0; i < text.length(); i++) {
    if (Character.isLetter(text.charAt(i))) {
        count++;
    }
}

System.out.println("Harf Sayısı: " + count);
