package ie.fieldublin.common.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntiry(D dto);

    D toDto(E entiry);

    List<E> toEntiry(List<D> dto);

    List<D> toDto(List<E> entiry);
}
