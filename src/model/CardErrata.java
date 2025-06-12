package model;
import java.time.LocalDate;

public class CardErrata {
    private int errataId;
    private int cardId;
    private LocalDate errataDate;
    private String description;
    private String sourceLink;

    public CardErrata(int errataId, int cardId, LocalDate errataDate, String description, String sourceLink) {
        this.errataId = errataId;
        this.cardId = cardId;
        this.errataDate = errataDate;
        this.description = description;
        this.sourceLink = sourceLink;
    }
    public int getErrataId() { return errataId; }
    public int getCardId() { return cardId; }
    public LocalDate getErrataDate() { return errataDate; }
    public String getDescription() { return description; }
    public String getSourceLink() { return sourceLink; }


    public void setErrataId(int errataId) { this.errataId = errataId; }
    public void setCardId(int cardId) { this.cardId = cardId; }
    public void setErrataDate(LocalDate errataDate) { this.errataDate = errataDate; }
    public void setDescription(String description) { this.description = description; }
    public void setSourceLink(String sourceLink) { this.sourceLink = sourceLink; }


    @Override
    public String toString() {
        return "Errata [" + errataDate + "] " + description + (sourceLink != null ? " (" + sourceLink + ")" : "");
    }
}
