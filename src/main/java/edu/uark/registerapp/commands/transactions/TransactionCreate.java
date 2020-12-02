package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionCreate {
	void execute(List<ProductEntity> products, List<Integer> amount) {
		long transactionTotal = 0L;
		final List<TransactionEntryEntity> transactionEntryEntities = new LinkedList<>();

		// For product entities in product repository
		// Since this is a dummy command, this seems to just add x amount of every product as a demo
		for (int i = 0; i < products.size(); i++) {

			// Number of products of this type being purchased??
			// For Dummy create maybe why the number is being randomized
			ProductEntity tempProduct = products.get(i);
			int purchasedQuantity = amount.get(i);

			// Adds to total cost of transaction
			transactionTotal += (tempProduct.getPrice() * purchasedQuantity);

			// Adds product to the list of transactionEntries
			transactionEntryEntities.add(
				(new TransactionEntryEntity())
					.setPrice(tempProduct.getPrice())
					.setProductId(tempProduct.getId())
					.setQuantity(purchasedQuantity));
		}

		// Creates Transaction with information gathered above
		this.createTransaction(
			transactionEntryEntities,
			transactionTotal);
	}

	// Helper methods
	@Transactional
	private void createTransaction(
		final List<TransactionEntryEntity> transactionEntryEntities,
		final long transactionTotal
	) {

		final TransactionEntity transactionEntity =
			this.transactionRepository.save(
				(new TransactionEntity(this.employeeId, transactionTotal, 1)));

		for (TransactionEntryEntity transactionEntryEntity : transactionEntryEntities) {
			transactionEntryEntity.setTransactionId(transactionEntity.getId());

			this.transactionEntryRepository.save(transactionEntryEntity);
		}
	}

	// Properties
	private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public TransactionCreate setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionEntryRepository transactionEntryRepository;
}
