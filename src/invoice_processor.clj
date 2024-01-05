;; src/invoice_processor.clj

(ns invoice-processor
  (:require [clojure.edn :as edn]))

(defn filter-invoice-items [invoice]
  (->> invoice
       :invoice/items
       (filter (fn [item]
                 (or (some #(= (:tax/category %) :iva) (:taxable/taxes item))
                     (some #(= (:retention/category %) :ret_fuente) (:retentionable/retentions item))
                 )
               )
       )
  )
)

(def invoice (edn/read-string (slurp "invoice.edn")))

(def filtered-items (filter-invoice-items invoice))

(prn filtered-items)