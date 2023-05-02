package br.com.fiap.MiaDBD.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    private String description;

    @NotBlank(message = "A categoria é obrigatória")
    @Size(min = 3, max = 50)
    private String category;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

}
