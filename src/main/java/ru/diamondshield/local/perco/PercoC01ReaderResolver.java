package ru.diamondshield.local.perco;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diamondshield.local.entity.LocalReader;
import ru.diamondshield.local.repository.LocalReaderRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PercoC01ReaderResolver {

    private final LocalReaderRepository readerRepository;

    public PercoC01ReaderResolver(LocalReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Transactional(readOnly = true)
    public Optional<LocalReader> resolve(UUID controllerId,
                                         Integer percoNumber,
                                         Integer percoDirection) {
        if (controllerId == null || percoNumber == null || percoDirection == null) {
            return Optional.empty();
        }

        return readerRepository.findFirstByControllerIdAndPercoExdevNumberAndPercoDirection(
                controllerId,
                percoNumber,
                percoDirection
        );
    }
}