package info.developia.hodor.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import info.developia.hodor.HodorException;
import info.developia.hodor.annotation.Hodor;
import info.developia.hodor.annotation.HoldTheDoor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
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
        roundEnvironment.getElementsAnnotatedWith(info.developia.hodor.annotation.Hodor.class).stream().findFirst().ifPresent(element -> {
            Element method = roundEnvironment.getElementsAnnotatedWith(info.developia.hodor.annotation.HoldTheDoor.class).stream().findFirst()
                    .orElseThrow(() -> new HodorException("Hodor does not know what door to hold"));

            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.holdTheDoor((a) -> $L.$L(args),(t) -> $L.$L(t))", info.developia.hodor.Hodor.class,
                            element.getEnclosingElement().getSimpleName(), element.getSimpleName(),
                            method.getEnclosingElement().getSimpleName(), method.getSimpleName())
                    .build();

            TypeSpec hodorWrapper = TypeSpec.classBuilder(element.getEnclosingElement().getSimpleName() + "Hodor")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .build();

            try {
                String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
                JavaFile.builder(packageName, hodorWrapper).build().writeTo(filer);
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

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_16;
    }
}
