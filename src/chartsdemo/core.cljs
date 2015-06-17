(ns ^:figwheel-always chartsdemo.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(def app-state (atom {:text "Hello world!"}))


(defn draw-graph-nv [owner div-id]
  (.addGraph js/nv
             (fn []
               (let [chart (.. js/nv -models discreteBarChart
                               (x #(.-label %))
                               (y #(.-value %))
                               (staggerLabels false)
                               (tooltips false)
                               (id "donut1")
                               (duration 350))
                     data (clj->js [{:key "foo"
                                     :values [{:label "deposits" :value 4000}
                                              {:label "withdrawls" :value -1000}]}])]
                 (.. js/d3 (select (str "#" div-id " svg"))
                     (datum (clj->js data))
                     (call chart)))))
  )

(defn chart-config []
  {:chart {:type "column"}
   :title {:text "My super chart"
           :style {:fontWeight "bold"
                   :fontSize "25px"}}
   :xAxis {:categories ["deposits" "withdrawals"]}
   :credits {:enabled false}
   :series [{:title "Serie title"
             :showInLegend false
             :name "Serie name"
             :data [[456] [-125]]
             :lineWidth 1
             :color "#F28900"}
            ]}
  )

(defn draw-chart []
  (js/$ (fn []
          (.highcharts (js/$ "#chartdiv")
                       (clj->js (chart-config))))))


(defn graph-view [cursor owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil
        (dom/div #js {:id "chartdiv"}
          (dom/svg nil))
        (dom/div #js {:id "nvd3div"}
          (dom/svg nil))))
    om/IDidMount
    (did-mount [_]
      (draw-chart)
      (draw-graph-nv owner "nvd3div")))
  )

(om/root
  (fn [data owner]
    (reify om/IRender
      (render [_]
        (dom/div nil
          (dom/h1 nil (:text data))
          (om/build graph-view nil)))))
  app-state
  {:target (. js/document (getElementById "app"))})


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

