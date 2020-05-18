package be.rommens.hera.dataset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:40
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString@EqualsAndHashCode
@Builder
public class Issue {

    private String issue;
    private String url;
    private String date;
    private List<String> pages;
}
