package info.developia.hodor.annotation.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import info.developia.hodor.HodorException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

public class HodorProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        roundEnvironment.getElementsAnnotatedWith(Hodor.class).stream().findFirst().ifPresent(element -> {
            Element method = roundEnvironment.getElementsAnnotatedWith(HoldTheDoor.class).stream().findFirst()
                    .orElseThrow(() -> new HodorException("Hodor does not know what door to hold"));

            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.holdTheDoor((a) -> $L.$L(args),(t) -> $L.$L(t))", info.developia.hodor.Hodor.class,
                            element.getEnclosingElement().getSimpleName(), element.getSimpleName(),
                            method.getEnclosingElement().getSimpleName(), method.getSimpleName())
                    .build();

            TypeSpec hodorWapper = TypeSpec.classBuilder(element.getSimpleName() + "Hodor")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .build();

            try {
                String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
                JavaFile.builder(packageName, hodorWapper).build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                throw new HodorException("Hodor doesn't know how to hold the door", e);
            }
            messager.printMessage(Diagnostic.Kind.NOTE, "Hodor knows what to do!");
        });
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Hodor.class.getCanonicalName(), HoldTheDoor.class.getCanonicalName());
    }

    private void error(String message, Element element) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void error(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    private void note(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }
}
