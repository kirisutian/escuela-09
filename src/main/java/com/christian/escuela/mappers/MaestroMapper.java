package com.christian.escuela.mappers;

import com.christian.escuela.dto.datos.DatosCurso;
import com.christian.escuela.dto.maestros.MaestroRequest;
import com.christian.escuela.dto.maestros.MaestroResponse;
import com.christian.escuela.entities.Grupo;
import com.christian.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro> {

    private final CursoMapper cursoMapper;

    @Override
    public Maestro requestAEntidad(MaestroRequest request) {

        if(request == null) return null;

        return Maestro.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().trim().toLowerCase())
                .telefono(request.telefono().trim())
                .build();
    }

    @Override
    public MaestroResponse entidadAResponse(Maestro entidad) {

        if(entidad == null) return null;

        List<DatosCurso> cursos = entidadADatosCurso(entidad);

        return new MaestroResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                cursos);
    }

    private List<DatosCurso> entidadADatosCurso(Maestro entidad) {

        if (entidad == null) return List.of();

        return entidad.getGrupos().stream()
                .map(Grupo::getCurso)
                .map(cursoMapper::entidadADatosCurso)
                .toList();
    }
}
