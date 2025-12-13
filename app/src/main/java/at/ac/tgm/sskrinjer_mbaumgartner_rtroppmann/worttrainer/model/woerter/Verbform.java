package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

public enum Verbform {
        POSITIV(1),
        KOMPARATIV(2),
        SUPERLATIV(3);
        int number;
        Verbform(int number) {
            this.number = number;
        }
        public int getNumber() {
            return number;
        }

}
