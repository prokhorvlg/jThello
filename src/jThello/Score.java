package jThello;

public class Score implements Comparable<Score> {
    public String rank;
    public String name;
    public String score;
    Score(String _rank, String _name, String _score) {
        rank = _rank;
        name = _name;
        score = _score;
    }

    @Override
    public int compareTo(Score i) {
        return (Integer.compare(Integer.parseInt(this.score), Integer.parseInt(i.score)));
    }
}
