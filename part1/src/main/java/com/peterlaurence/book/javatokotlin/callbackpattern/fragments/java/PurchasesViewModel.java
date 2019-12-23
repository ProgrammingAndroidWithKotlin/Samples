package com.peterlaurence.book.javatokotlin.callbackpattern.fragments.java;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.peterlaurence.book.javatokotlin.callbackpattern.core.java.UserPurchases;

import java.util.List;

public class PurchasesViewModel extends ViewModel {
    private BillingClient billingClient;
    private final String user;

    private MutableLiveData<UserPurchases> purchases;

    PurchasesViewModel(BillingClient billingClient,
                       String user) {
        this.billingClient = billingClient;
        this.user = user;
    }

    private void getUserPurchases(String user) {
        billingClient.init(provider -> {
            // this is called from a background thread
            if (provider != null) {
                provider.fetchPurchases(user, purchases -> {
                    this.purchases.postValue(new UserPurchases(user, purchases));
                });
            }
        });
    }

    LiveData<UserPurchases> getPurchasesLiveData() {
        if (purchases == null) {
            purchases = new MutableLiveData<>();
            getUserPurchases(user);
        }
        return purchases;
    }

    public interface BillingClient {
        interface BillingCallback {
            void onInitDone(@Nullable PurchasesProvider provider);
        }

        void init(BillingCallback callback);
    }

    public interface PurchasesProvider {
        interface PurchaseFetchCallback {
            void onPurchaseFetchDone(List<String> purchases);
        }

        void fetchPurchases(String user, PurchaseFetchCallback callback);
    }
}
