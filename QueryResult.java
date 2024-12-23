public class QueryResult {
    private String query;
    private String result;

    public QueryResult(String query, String result) {
        this.query = query;
        this.result = result;
    }

    public String getQuery() {
        return query;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "{\"Query\": \"" + query + "\", \"Result\": \"" + result + "\"}";
    }
}