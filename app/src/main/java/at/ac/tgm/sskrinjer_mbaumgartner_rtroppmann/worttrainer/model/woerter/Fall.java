package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

public enum Fall {
    NOMINATIV(1),
    GENETIV(2),
    DAITV(3),
    AKKUSATIV(4);
    int number;
    Fall(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
}
