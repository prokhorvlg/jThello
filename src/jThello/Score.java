package jThello;

public class Score implements Comparable<Score> {
    public String name;
    public String score;
    Score(String _name, String _score) {
        name = _name;
        score = _score;
    }

    @Override
    public int compareTo(Score i) {
        return (Integer.compare(Integer.parseInt(this.score), Integer.parseInt(i.score)));
    }
}
