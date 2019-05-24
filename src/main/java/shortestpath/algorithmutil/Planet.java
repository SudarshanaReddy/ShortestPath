package shortestpath.algorithmutil;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planet {

    private String name;

    @Override
    public String toString() {
        return name;
    }

}