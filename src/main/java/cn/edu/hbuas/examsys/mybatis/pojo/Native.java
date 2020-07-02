package cn.edu.hbuas.examsys.mybatis.pojo;

public class Native {
    private String card;

    private String place;

    public Native(String card, String place) {
        this.card = card;
        this.place = place;
    }

    public Native() {
        super();
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card == null ? null : card.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }
}