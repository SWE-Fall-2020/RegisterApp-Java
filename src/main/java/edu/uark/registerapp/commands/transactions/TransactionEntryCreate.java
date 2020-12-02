package edu.uark.registerapp.commands.transactions;


import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.ProductEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionEntryCreate {
    @Transactional
    private void TransactionEntryCreate (ProductEntity product, int amount) {

    }
}
