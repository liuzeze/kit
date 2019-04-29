package lz.com.tools.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-29       创建class
 */
public class TrsImageView extends AppCompatImageView {

    public TrsImageView(Context context) {
        super(context);
        init();
    }

    public TrsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Matrix matrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        setImageMatrix(matrix);

        Observable<Event> touchStream = Observable.create(new ObservableOnSubscribe<Event>() {
            @Override
            public void subscribe(ObservableEmitter<Event> emitter) throws Exception {
                setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int pointerCount = event.getPointerCount();
                        if (pointerCount == 1) {
                            Event e = new Event();
                            e.action = event.getActionMasked();
                            e.p1 = new Vector(event.getX(), event.getY());
                            emitter.onNext(e);
                        } else if (pointerCount == 2) {
                            Event e = new Event();
                            e.action = event.getActionMasked();
                            e.p1 = new Vector(event.getX(0), event.getY(0));
                            e.p2 = new Vector(event.getX(1), event.getY(1));
                            emitter.onNext(e);
                        }
                        return true;
                    }
                });
            }
        }).share();

        Observable<Event> pointer1Down = touchStream.filter(new Predicate<Event>() {
            @Override
            public boolean test(Event e) throws Exception {
                return e.action == MotionEvent.ACTION_DOWN;
            }
        });
        Observable<Event> pointer2Down = touchStream.filter(new Predicate<Event>() {
            @Override
            public boolean test(Event e) throws Exception {
                return e.action == MotionEvent.ACTION_POINTER_DOWN;
            }
        });
        Observable<Event> pointerMove = touchStream.filter(new Predicate<Event>() {
            @Override
            public boolean test(Event e) throws Exception {
                return e.action == MotionEvent.ACTION_MOVE;
            }
        });
        Observable<Event> pointer2Up = touchStream.filter(new Predicate<Event>() {
            @Override
            public boolean test(Event e) throws Exception {
                return e.action == MotionEvent.ACTION_POINTER_UP;
            }
        });
        Observable<Event> pointer1Up = touchStream.filter(new Predicate<Event>() {
            @Override
            public boolean test(Event e) throws Exception {
                return e.action == MotionEvent.ACTION_UP;
            }
        });

        Observable<Vector> delta1 = Observable.combineLatest(pointerMove, pointerMove.skip(1), new BiFunction<Event, Event, Vector>() {
            @Override
            public Vector apply(Event prev, Event cur) throws Exception {
                return prev.p1.subtract(cur.p1);
            }
        });
        Observable<Delta> delta2 = Observable.combineLatest(pointerMove, pointerMove.skip(1), Delta::delta);

      pointer1Down
                .mergeWith(pointer2Up)
                .flatMap(new Function<Event, ObservableSource<? extends Vector>>() {
                    @Override
                    public ObservableSource<? extends Vector> apply(Event e) throws Exception {
                        return delta1.takeUntil(pointer1Up).takeUntil(pointer2Down);
                    }
                })
                .subscribe(v -> {
                    matrix.postTranslate(v.x, v.y);
                    setImageMatrix(matrix);
                });

       pointer2Down
                .flatMap(new Function<Event, ObservableSource<? extends Delta>>() {
                    @Override
                    public ObservableSource<? extends Delta> apply(Event e) throws Exception {
                        return delta2.takeUntil(pointer2Up);
                    }
                })
                .subscribe(new Consumer<Delta>() {
                    @Override
                    public void accept(Delta d) throws Exception {
                        matrix.postTranslate(d.translate.x, d.translate.y);
                        matrix.postRotate(d.rotate, d.center.x, d.center.y);
                        matrix.postScale(d.scale, d.scale, d.center.x, d.center.y);
                        TrsImageView.this.setImageMatrix(matrix);
                    }
                });
    }


    static class Delta {
        Vector center;
        Vector translate;
        float scale;
        float rotate;

        static Delta delta(Event prev, Event cur) {
            Delta delta = new Delta();
            delta.center = cur.center();
            delta.translate = prev.center().subtract(cur.center());
            delta.scale = prev.length() / cur.length();
            delta.rotate = cur.vector().angle(prev.vector());
            return delta;
        }
    }


    static class Event {
        int action;
        Vector p1, p2;
        Vector center() { return p1.add(p2).multiply(0.5f); }
        float length() { return p1.subtract(p2).length(); }
        Vector vector() { return p1.subtract(p2); }
    }


    static class Vector {
        float x, y;
        Vector(float x, float y) { this.x = x; this.y = y; }
        Vector add(Vector v) { return new Vector(x + v.x, y + v.y); }
        Vector subtract(Vector v) { return new Vector(x - v.x, y - v.y); }
        Vector multiply(float s) { return new Vector(x * s, y * s); }
        float dot(Vector v) { return x * v.x + y * v.y; }
        float cross(Vector v) { return x * v.y - y * v.x; }
        float length() { return (float)Math.sqrt(x * x + y * y); }
        float angle(Vector b) {
            float cos = dot(b) / (length() * b.length());
            cos = Math.max(-1.0f, Math.min(1.0f, cos));
            float degree = (float)Math.toDegrees(Math.acos(cos));
            return cross(b) > 0 ? degree : -degree;
        }
    }

}