package net.sf.jabref.logic.integrity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import net.sf.jabref.logic.integrity.IntegrityCheck.Checker;
import net.sf.jabref.logic.journals.JournalAbbreviationRepository;
import net.sf.jabref.logic.l10n.Localization;
import net.sf.jabref.model.entry.BibEntry;

public class JournalInAbbreviationListChecker implements Checker {

    private final String field;
    private final JournalAbbreviationRepository abbreviationRepository;

    public JournalInAbbreviationListChecker(String field, JournalAbbreviationRepository abbreviationRepository) {
        this.field = Objects.requireNonNull(field);
        this.abbreviationRepository = Objects.requireNonNull(abbreviationRepository);
    }

    @Override
    public List<IntegrityMessage> check(BibEntry entry) {
        Optional<String> value = entry.getField(field);
        if (!value.isPresent()) {
            return Collections.emptyList();
        }

        final String journal = value.get();
        if (!abbreviationRepository.isKnownName(journal)) {
            return Collections
                    .singletonList(new IntegrityMessage(Localization.lang("journal not found in abbreviation list"), entry, field));
        }

        return Collections.emptyList();
    }
}
