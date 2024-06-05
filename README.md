# Memo1 - Backend API

This API allows:

1. Create, read, update and delete bank accounts.
2. Create both withdraw and deposit transactions.
3. Delete transactions, with their correspondent rollback.
4. Search transactions by account CBU.
5. Search transaction by its ID.

Additionally, it supports deposit promos:
> Bank account promo, get 10% extra in your $2000+ deposits, up to $500

Business rules:

1. Negative or null sums are not allowed for deposit.
2. Withdrawal of sums greater than the account balance are not allowed.

Considerations:

1. Negative or null sums are not allowed for withdrawal.
2. Transactions to non-existent accounts are not allowed.
3. Deleting transactions from deleted accounts is allowed.
4. Deleting a deposit transaction with a promo applied should roll back the promo.