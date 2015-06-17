# chartsdemo
ClojureScript example of using [Highcharts](www.highcharts.com) and [nvd3](nvd3.org).
It was live coded at Paris Clojure Meetup on June 16th 2015.

Based on fighweel + om template.

## Room for improvement:
- use Sablono
- use more charting and drawing libs (fusioncharts, raphael.js, d3.js...)
- avoid chart redrawal when updating app state (e.g. via '''transact!'''). The code to redraw the whole graph on state update is shown on @annapawlicka [repo](https://github.com/annapawlicka/om-data-vis/blob/master/src-cljs/om-data-vis/chart.cljs#L38-L42). We'd have to update the data directly in the graph via the appropriate API (so, yes, mutation). 
